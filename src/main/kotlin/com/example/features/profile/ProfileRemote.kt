package com.example.features.profile

import com.example.database.users.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProfileReceiveRemote(val token: String)

@Serializable
data class ProfileResponseRemote(val imageUrl: String, val username: String)