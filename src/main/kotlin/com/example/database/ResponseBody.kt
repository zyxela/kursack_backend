package com.example.database

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody<T>(
    //val status:String,
    //val time:String,
    //  val message:String,
    val data:T
)