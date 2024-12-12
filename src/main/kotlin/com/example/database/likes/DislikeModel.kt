package com.example.database.likes

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object DislikeModel:Table("dislikes") {
    val id = DislikeModel.varchar("id", 36)
    val userId = DislikeModel.varchar("user_id", 36)
    val videoId = DislikeModel.varchar("video_id", 36)

    fun dislike(videoId: String, userId: String) = transaction {
        DislikeModel.insert {
            it[LikesModel.id] = UUID.randomUUID().toString()
            it[LikesModel.userId] = userId
            it[LikesModel.videoId] = videoId
        }
    }
}