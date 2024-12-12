package com.example.features.login

import com.example.database.ResponseBody
import com.example.database.users.UserModel
import com.example.services.HashService
import com.example.services.JWTService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class LoginController(
    private val call: ApplicationCall
) {
    suspend fun login() {
        val receive = call.receive<LoginReceiveRemote>()

        val userDTO = UserModel.fetchUser(receive.email)
        if (userDTO == null) {
            call.respond(HttpStatusCode.Conflict, "User not found")
            return
        }

        if (!HashService.verifyPassword(receive.password, userDTO.password)) {
            call.respond(HttpStatusCode.Conflict, "You've got the wrong door, boooy")
            return
        }

        val token = JWTService(call.application).createJWT(userDTO.id).toString()

        call.respond(ResponseBody(LoginResponseRemote(token)))
    }
}