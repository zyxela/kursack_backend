package com.example.features.profile

import com.example.database.ResponseBody
import com.example.database.users.UserModel
import com.example.services.JWTService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ProfileController (
    private val call:ApplicationCall
){
    suspend fun getProfileInfo(){
        //val receive = call.receive<ProfileReceiveRemote>()
        val jwt = JWTService(call.application)
        val authHeader = call.request.headers["Authorization"]
        val token = authHeader?.replace("Bearer ", "")
        val userDTO = UserModel.findUserById(jwt.extractId(token!!)!!)

        call.respond(ResponseBody(userDTO?.imageUrl?.let { ProfileResponseRemote(it, userDTO.username) }))
    }
}