package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.ForwardMessages
import me.thestars.orbit.database.table.MessageLikes
import me.thestars.orbit.database.table.Messages
import me.thestars.orbit.database.table.SavedMessages
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class Message(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Message>(Messages)

    var channelId by Messages.channelId
    var authorId by Messages.authorId
    var content by Messages.content
    var connection by Messages.connection
    var reference by Messages.reference
    var createdAt by Messages.createdAt

    val likes by MessageLike referrersOn MessageLikes.message
    val savedBy by SavedMessage referrersOn SavedMessages.message
}

class MessageLike(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageLike>(MessageLikes)

    var createdAt by MessageLikes.createdAt
    var user by User referencedOn MessageLikes.user
    var message by Message referencedOn MessageLikes.message
}

class SavedMessage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SavedMessage>(SavedMessages)

    var savedAt by SavedMessages.savedAt
    var user by User referencedOn SavedMessages.user
    var message by Message referencedOn SavedMessages.message
}

class ForwardMessage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ForwardMessage>(ForwardMessages)

    var originMessageId by ForwardMessages.originMessageId
    var originChannelId by ForwardMessages.originChannelId
    var forwardedMessageId by ForwardMessages.forwardedMessageId
    var forwardedChannelId by ForwardMessages.forwardedChannelId
    var connectionName by ForwardMessages.connectionName
    var originalAuthorId by ForwardMessages.originalAuthorId
    var webhookId by ForwardMessages.webhookId
    var webhookToken by ForwardMessages.webhookToken
    var createdAt by ForwardMessages.createdAt
}