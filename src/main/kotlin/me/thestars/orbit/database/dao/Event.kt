package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.CommandUsedEvents
import me.thestars.orbit.database.table.ConnectionCreateEvents
import me.thestars.orbit.database.table.ConnectionDeleteEvents
import me.thestars.orbit.database.table.GuildCreateEvents
import me.thestars.orbit.database.table.GuildDeleteEvents
import me.thestars.orbit.database.table.MessageSendEvents
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID


class CommandUsedEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommandUsedEvent>(CommandUsedEvents)

    var name by CommandUsedEvents.name
    var guildName by CommandUsedEvents.guildName
    var guildId by CommandUsedEvents.guildId
    var userId by CommandUsedEvents.userId
    var createdAt by CommandUsedEvents.createdAt
}

class GuildCreateEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GuildCreateEvent>(GuildCreateEvents)

    var name by GuildCreateEvents.name
    var guildId by GuildCreateEvents.guildId
    var ownerId by GuildCreateEvents.ownerId
    var createdAt by GuildCreateEvents.createdAt
}

class GuildDeleteEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GuildDeleteEvent>(GuildDeleteEvents)

    var name by GuildDeleteEvents.name
    var guildId by GuildDeleteEvents.guildId
    var ownerId by GuildDeleteEvents.ownerId
    var createdAt by GuildDeleteEvents.createdAt
}

class MessageSendEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageSendEvent>(MessageSendEvents)

    var messageId by MessageSendEvents.messageId
    var userId by MessageSendEvents.userId
    var guildId by MessageSendEvents.guildId
    var connectionName by MessageSendEvents.connectionName
    var createdAt by MessageSendEvents.createdAt
}


class ConnectionCreateEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ConnectionCreateEvent>(ConnectionCreateEvents)

    var name by ConnectionCreateEvents.name
    var guildId by ConnectionCreateEvents.guildId
    var userId by ConnectionCreateEvents.userId
    var createdAt by ConnectionCreateEvents.createdAt
}

class ConnectionDeleteEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ConnectionDeleteEvent>(ConnectionDeleteEvents)

    var name by ConnectionDeleteEvents.name
    var guildId by ConnectionDeleteEvents.guildId
    var userId by ConnectionDeleteEvents.userId
    var createdAt by ConnectionDeleteEvents.createdAt
}