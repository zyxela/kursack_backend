package com.example.features.homePage

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureHomeRemote() {
    routing {
        authenticate("auth-jwt") {
            get("/home") {

                val homeController = HomeController(call)
                homeController.getAllVideos()


                /*  val principal = call.principal<JWTPrincipal>()
                  val userid = principal!!.payload.getClaim("id").asString()
                  val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                  call.respondText("Hello, $userid! Token is expired at $expiresAt ms.")*/
            }
        }
    }
}