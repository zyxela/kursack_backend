package com.example.features.images

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.imagesModule() {

    routing {
        staticFiles("/preview_images/", File("./preview_images/"))
        staticFiles("/channel_images/", File("./channel_images/"))
    }
}