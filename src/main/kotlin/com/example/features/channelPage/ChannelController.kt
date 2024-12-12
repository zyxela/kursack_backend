package com.example.features.channelPage

import com.example.database.ResponseBody
import com.example.database.subscribes.SubscribeDTO
import com.example.database.subscribes.SubscribeModel
import com.example.database.users.UserModel
import com.example.database.videos.VideoModel
import com.example.services.JWTService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*


class ChannelController(
    val call: ApplicationCall
) {
    suspend fun getChannelInfo() {
        val channelId = call.parameters["channelId"]!!
        val videos = VideoModel.fetchVideosById(channelId)
        val user = UserModel.findUserById(channelId)
        call.respond(ResponseBody(ChannelResponseRemote(user!!, videos)))

    }

    suspend fun subscribeToChannel() {
        val receive = call.receive<SubscribeReceiveRemote>()
        val jwt = JWTService(call.application)
        val authHeader = call.request.headers["Authorization"]
        val token = authHeader?.replace("Bearer ", "")
        val userId = jwt.extractId(token!!)
        val id = UUID.randomUUID().toString()

        SubscribeModel.subscribe(SubscribeDTO(id, userId!!, receive.channelId))
        call.respond(true)
    }

}