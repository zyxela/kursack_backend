package com.example.features.subscribes

import com.example.database.subscribes.SubscribeDTO
import kotlinx.serialization.Serializable

data class MySubscribesResponseRemote(
    val data: List<SubscribeDTO>
)

@Serializable
data class SubscribeResponseRemote(
    val id:String,
    val name:String,
    val imageUrl:String
)