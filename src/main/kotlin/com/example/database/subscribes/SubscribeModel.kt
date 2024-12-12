package com.example.database.subscribes

import com.example.database.users.UserDTO
import com.example.database.users.UserModel
import com.example.features.subscribes.SubscribeResponseRemote
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object SubscribeModel : Table("subscribers") {
    val id = SubscribeModel.varchar("id", 36)
    val userId = SubscribeModel.varchar("user_id", 36)
    val channelId = SubscribeModel.varchar("channel_id", 36)

    fun subscribe(subscribeDTO: SubscribeDTO) {
        transaction {
            SubscribeModel.insert {
                it[id] = subscribeDTO.id
                it[userId] = subscribeDTO.userId
                it[channelId] = subscribeDTO.channelId
            }
        }
    }

    fun fetchSubscribesByUserId(userId: String): List<SubscribeResponseRemote> {
        return transaction {
            UserModel
                .join(SubscribeModel, JoinType.INNER, UserModel.id, SubscribeModel.channelId)
                .selectAll()
                .where { SubscribeModel.userId eq userId }
                .map {
                    SubscribeResponseRemote(
                        id = it[SubscribeModel.channelId],
                        name = it[UserModel.username],
                        imageUrl = it[UserModel.imageUrl],

                    )
                }

        }
    }

}