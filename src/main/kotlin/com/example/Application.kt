package com.example

import com.example.features.channelPage.configureChannelRouting
import com.example.features.homePage.configureHomeRemote
import com.example.features.images.imagesModule
import com.example.features.login.configureLoginRouting
import com.example.features.profile.configureProfileRouting
import com.example.features.registration.configureRegistrationRouting
import com.example.features.subscribes.configureMySubscribesRouting
import com.example.features.videos.configureVideoRouting
import com.example.pluginsSamle.configureRouting
import com.example.pluginsSamle.configureSerialization
import com.example.pluginsSamle.configureSockets
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/kursach",
        driver = "org.postgresql.Driver",
        user ="postgres",
        password = "postgres"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(CORS) {
       anyHost()
    }
    configureLoginRouting()
    configureRegistrationRouting()
    configureSockets()
    configureSerialization()
    configureRouting()
    configureHomeRemote()
    imagesModule()
    configureProfileRouting()
    configureChannelRouting()
    configureMySubscribesRouting()

    configureVideoRouting()

}


