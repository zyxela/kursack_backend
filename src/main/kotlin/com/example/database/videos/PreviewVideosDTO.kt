package com.example.database.videos

import kotlinx.serialization.Serializable

@Serializable
data class PreviewVideosDTO(
    val id: String,
    val title: String,
    val previewUrl: String,
    val channelId: String,
    val channelName: String,
    val channelImageUrl: String
)