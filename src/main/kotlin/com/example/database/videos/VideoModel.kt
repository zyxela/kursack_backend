package com.example.database.videos

import com.example.database.comments.CommentsModel
import com.example.database.comments.CommentsModel.select
import com.example.database.likes.DislikeModel
import com.example.database.likes.DislikeModel.select
import com.example.database.likes.LikesModel
import com.example.database.likes.LikesModel.select
import com.example.database.users.UserModel
import com.example.features.videos.SubInfoResponse
import com.example.features.videos.VideoResponseRemote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object VideoModel : Table("videos") {
    val id = VideoModel.varchar("id", 36)
    val title = VideoModel.varchar("title", 30)
    val description = VideoModel.varchar("description", 30)
    val userId = VideoModel.varchar("user_id", 30)
    val previewUrl = VideoModel.varchar("preview_url", 30)
    val videoUrl = VideoModel.varchar("video_url", 30)

    fun insert(videoDTO: VideoDTO) = transaction {
        VideoModel.insert {
            it[id] = videoDTO.id
            it[title] = videoDTO.title
            it[description] = videoDTO.description
            it[userId] = videoDTO.userId
            it[videoUrl] = videoDTO.videoUrl
        }
    }

    fun fetchPreviewVideos(): List<PreviewVideosDTO>? = try {
        transaction {
            VideoModel.join(UserModel, JoinType.INNER, userId, UserModel.id).selectAll().map {
                PreviewVideosDTO(
                    id = it[VideoModel.id],
                    title = it[title],
                    previewUrl = it[previewUrl],
                    channelId = it[UserModel.id],
                    channelName = it[UserModel.username],
                    channelImageUrl = it[UserModel.imageUrl],
                )
            }

        }
    } catch (e: Exception) {
        println(e.message)
        null
    }

    fun fetchVideosById(channelId: String): List<PreviewVideosDTO>? = try {
        transaction {
            VideoModel.join(UserModel, JoinType.INNER, userId, UserModel.id).selectAll()
                .where { userId.eq(channelId) }
                .map {
                    PreviewVideosDTO(
                        id = it[VideoModel.id],
                        title = it[title],
                        previewUrl = it[previewUrl],
                        channelId = it[UserModel.id],
                        channelName = it[UserModel.username],
                        channelImageUrl = it[UserModel.imageUrl],
                    )
                }

        }
    } catch (e: Exception) {
        println(e.message)
        null
    }

    fun findById(videoId: String): VideoResponseRemote? = try {
        transaction {
            VideoModel.selectAll().where { VideoModel.id.eq(videoId) }.first().let {
                VideoResponseRemote(
                    VideoDTO(
                        id = it[VideoModel.id],
                        title = it[title],
                        description = it[description],
                        userId = it[userId],
                        videoUrl = it[videoUrl]
                    )
                )
            }


        }
    } catch (e: Exception) {
        println(e.message)
        null
    }

    fun fetchSubInfo(videoId: String): SubInfoResponse? = try {
        transaction {
            val likesCount = LikesModel.select { LikesModel.videoId eq videoId }.count()
            val dislikesCount = DislikeModel.select { DislikeModel.videoId eq videoId }.count()
            val commentsList = CommentsModel
                .select { CommentsModel.videoId eq videoId }
                .map { it[CommentsModel.text] }

            SubInfoResponse(
                likes = likesCount.toInt(),
                dislike = dislikesCount.toInt(),
                comments = commentsList
            )
        }
    } catch (e: Exception) {
        println(e.message)
        null
    }

}