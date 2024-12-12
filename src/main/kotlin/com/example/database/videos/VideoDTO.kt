package com.example.database.videos

import kotlinx.serialization.Serializable

@Serializable
data class VideoDTO(
    val id: String,
    val title: String,
    val description: String,
    val userId:String,
    val videoUrl: String
)