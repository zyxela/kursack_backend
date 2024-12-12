package com.example.features.homePage

import com.example.database.videos.VideoModel
import io.ktor.server.application.*
import io.ktor.server.response.*

class HomeController(
    private val call: ApplicationCall
) {
    suspend fun getAllVideos() {
        //val receive = call.receive<HomeReceiveRemote>()
        val data = VideoModel.fetchPreviewVideos()

        call.respond(HomeResponseRemote(data))
    }

}