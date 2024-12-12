package com.example.features.subscribes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureMySubscribesRouting() {
    routing {
        authenticate ("auth-jwt") {
            get("/my-subscribes") {
                MySubscribesController(call).getMySubscribes()

            }
        }
    }
}