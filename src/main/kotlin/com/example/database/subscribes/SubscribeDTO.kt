package com.example.database.subscribes

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeDTO (
    val id:String,
    val userId:String,
    val channelId:String
)