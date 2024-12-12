package com.example.features.channelPage

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureChannelRouting() {
    routing {
        authenticate("auth-jwt") {
            get("/channel/{channelId}") {
                val channelController = ChannelController(call)
                channelController.getChannelInfo()
            }

            post("/subscribe") {
                val channelController = ChannelController(call)
                channelController.subscribeToChannel()
            }
        }

    }
}