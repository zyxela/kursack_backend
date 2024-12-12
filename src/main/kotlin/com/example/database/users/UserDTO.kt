package com.example.database.users

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id:String,
    val username:String,
    val imageUrl: String,
    val email:String,
    val password:String
) {

}
