package com.example.features.profile

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.postgresql.gss.MakeGSS.authenticate

fun Application.configureProfileRouting() {

    routing {
        authenticate("auth-jwt") {
            get("/profile") {
                ProfileController(call).getProfileInfo()
            }
        }
    }
}