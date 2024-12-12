package com.example.features.registration

import com.example.database.ResponseBody
import com.example.database.users.UserDTO
import com.example.database.users.UserModel
import com.example.services.HashService
import com.example.services.JWTService
import com.example.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class RegisterController(
    private val call: ApplicationCall
) {
    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            return
        }

        val userDTO = UserModel.fetchUser(registerReceiveRemote.email)
        println(userDTO?.email)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return
        }

        val id = UUID.randomUUID().toString()
        val hashedPassword = HashService.hashPassword(registerReceiveRemote.password)
        try {
            UserModel.insert(
                UserDTO(
                    id = id,
                    username = registerReceiveRemote.username,
                    imageUrl = "",
                    email = registerReceiveRemote.email,
                    password = hashedPassword,
                )
            )
        } catch (e: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, e.localizedMessage)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
        }

        val jwtService = JWTService(call.application)
        val token = jwtService.createJWT(id)

        call.respond(ResponseBody(RegisterResponseRemote(token = token!!)))
    }


}