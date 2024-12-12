package com.example.database.comments

import com.example.database.likes.LikesModel
import org.jetbrains.exposed.sql.Table

object CommentsModel:Table("comments") {
    val id = CommentsModel.varchar("id", 36)
    val userId = CommentsModel.varchar("user_id", 36)
    val videoId = CommentsModel.varchar("video_id", 36)
    val text = CommentsModel.varchar("text", 40)
}