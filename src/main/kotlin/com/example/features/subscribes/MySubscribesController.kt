package com.example.features.subscribes

import com.example.database.ResponseBody
import com.example.database.subscribes.SubscribeModel
import com.example.services.JWTService
import io.ktor.server.application.*
import io.ktor.server.response.*


class MySubscribesController(
    private val call: ApplicationCall
) {
    suspend fun getMySubscribes() {
        val jwt = JWTService(call.application)
        val authHeader = call.request.headers["Authorization"]
        val token = authHeader?.replace("Bearer ", "")
        val userId = jwt.extractId(token!!)
        val data = SubscribeModel.fetchSubscribesByUserId(userId!!)
        call.respond(ResponseBody(data))
    }
}