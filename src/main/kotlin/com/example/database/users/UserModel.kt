package com.example.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("users") {
    val id = UserModel.varchar("id", 36)
    val username = UserModel.varchar("username", 30)
    private val email = UserModel.varchar("email", 30)
    private val password = UserModel.varchar("password", 100)
    val imageUrl = UserModel.varchar("image_url", 52)

    fun insert(userDTO: UserDTO) {
        transaction {
            UserModel.insert {
                it[id] = userDTO.id
                it[username] = userDTO.username
                it[email] = userDTO.email
                it[password] = userDTO.password
                it[imageUrl] = ""
            }
        }
    }

    fun fetchUser(email: String): UserDTO? {
        return try {
            transaction {
                val userModel = UserModel.selectAll().where { UserModel.email.eq(email) }.first()
                println(userModel.toString())
                UserDTO(
                    id = userModel[UserModel.id],
                    password = userModel[password],
                    username = userModel[username],
                    imageUrl = userModel[imageUrl],
                    email = userModel[UserModel.email]
                )

            }
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    fun findUserById(id: String): UserDTO? {
        return try {
            transaction {
                val userModel = UserModel.selectAll().where { UserModel.id.eq(id) }.first()
                println(userModel.toString())
                UserDTO(
                    id = userModel[UserModel.id],
                    password = userModel[password],
                    username = userModel[username],
                    imageUrl = userModel[imageUrl],
                    email = userModel[UserModel.email]
                )

            }
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

}