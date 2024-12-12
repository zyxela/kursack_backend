package com.example.features.homePage

import com.example.database.videos.PreviewVideosDTO
import com.example.database.videos.VideoDTO
import kotlinx.serialization.Serializable

@Serializable
data class HomeReceiveRemote(
    val token: String
)

@Serializable
data class HomeResponseRemote(
    val data: List<PreviewVideosDTO>?
)