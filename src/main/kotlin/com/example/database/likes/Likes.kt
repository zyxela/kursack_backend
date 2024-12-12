package com.example.database.likes

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object LikesModel:Table("likes") {
    val id = LikesModel.varchar("id", 36)
    val userId = LikesModel.varchar("user_id", 36)
    val videoId = LikesModel.varchar("video_id", 36)

    fun like(videoId: String, userId: String) = transaction {
        LikesModel.insert {
            it[LikesModel.id] = UUID.randomUUID().toString()
            it[LikesModel.userId] = userId
            it[LikesModel.videoId] = videoId
        }
    }
}