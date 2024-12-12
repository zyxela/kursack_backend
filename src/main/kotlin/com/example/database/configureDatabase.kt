package com.example.database

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*


fun Application.configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/kursach",
        user = "postgresql",
        password = "root"
    )
}