package com.example.features.channelPage

import com.example.database.users.UserDTO
import com.example.database.videos.PreviewVideosDTO
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeReceiveRemote(
    val channelId: String
)

@Serializable
data class ChannelResponseRemote(
    val userDTO: UserDTO,
    val videos: List<PreviewVideosDTO>?
)