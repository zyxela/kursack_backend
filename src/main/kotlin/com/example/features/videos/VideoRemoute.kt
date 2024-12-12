package com.example.features.videos

import com.example.database.videos.VideoDTO
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponseRemote(val data: VideoDTO)

@Serializable
data class SubInfoResponse(val likes:Int, val dislike:Int, val comments:List<String>)