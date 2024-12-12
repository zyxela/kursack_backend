package com.example.features.videos

import com.example.database.likes.DislikeModel
import com.example.database.likes.LikesModel
import com.example.database.users.UserModel
import com.example.database.videos.VideoModel
import com.example.services.JWTService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.RandomAccessFile

fun Application.configureVideoRouting() {
    routing {
        // Эндпоинт для потокового видео
        get("/video/{videoName}") {
            val videoName = call.parameters["videoName"]

            if (videoName == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val videoFile = File("videos/$videoName")
            if (!videoFile.exists()) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            val range = call.request.headers[HttpHeaders.Range]?.removePrefix("bytes=")
            if (range == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val (start, end) = range.split("-").map { it.toLongOrNull() }
            val fileLength = videoFile.length()
            val rangeStart = start ?: 0
            val rangeEnd = end ?: (fileLength - 1)

            if ((rangeStart >= fileLength) || (rangeEnd >= fileLength) || (rangeStart > rangeEnd)) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val contentLength = rangeEnd - rangeStart + 1
            val contentRange = "bytes $rangeStart-$rangeEnd/$fileLength"

            call.response.headers.append(HttpHeaders.ContentType, "video/mp4")
            call.response.headers.append(HttpHeaders.ContentLength, contentLength.toString())
            call.response.headers.append(HttpHeaders.AcceptRanges, "bytes")
            call.response.headers.append(HttpHeaders.ContentRange, contentRange)

            val randomAccessFile = RandomAccessFile(videoFile, "r")
            randomAccessFile.seek(rangeStart)

            call.respondOutputStream {
                val buffer = ByteArray(1024 * 8)
                var bytesLeft = contentLength
                while (bytesLeft > 0) {
                    val bytesRead = randomAccessFile.read(buffer, 0, minOf(buffer.size.toLong(), bytesLeft).toInt())
                    if (bytesRead == -1) break
                    write(buffer, 0, bytesRead)
                    bytesLeft -= bytesRead
                }
            }

            randomAccessFile.close()
        }

        get("/videoInfo/{videoId}") {
            val videoId = call.parameters["videoId"]
            val video = VideoModel.findById(videoId!!)
            call.respond(video!!)
        }

        get("/subinfo/{videoId}") {
            val videoId = call.parameters["videoId"]
            val video = VideoModel.fetchSubInfo(videoId!!)
            call.respond(video!!)
        }

       /* authenticate("auth-jwt") {

            get("/like/{videoId}") {
                GlobalScope.launch {
                    val videoId = call.parameters["videoId"]
                    val jwt = JWTService(call.application)
                    val authHeader = call.request.headers["Authorization"]
                    val token = authHeader?.replace("Bearer ", "")
                    val id = jwt.extractId(token!!)
                    LikesModel.like(videoId!!, id!!)
                }

            //    call.respond(HttpStatusCode.OK,)
            }

            get("/dislike/{videoId}") {
                GlobalScope.launch {
                    val videoId = call.parameters["videoId"]
                    val jwt = JWTService(call.application)
                    val authHeader = call.request.headers["Authorization"]
                    val token = authHeader?.replace("Bearer ", "")
                    val id = jwt.extractId(token!!)

                    DislikeModel.dislike(videoId!!, id!!)
                    call.respond(HttpStatusCode.OK,)
                }

            }
        }*/
    }
}