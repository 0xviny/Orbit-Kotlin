package me.thestars.orbit.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object CommandUsedEvents : UUIDTable("events_command_used") {
    val name = varchar("name", 200)
    val guildName = varchar("guild_name", 200)
    val guildId = uuid("guild_id")
    val userId = uuid("user_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object GuildCreateEvents : UUIDTable("events_guild_create") {
    val name = varchar("name", 200)
    val guildId = uuid("guild_id").uniqueIndex()
    val ownerId = uuid("owner_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}


object GuildDeleteEvents : UUIDTable("events_guild_delete") {
    val name = varchar("name", 200)
    val guildId = uuid("guild_id")
    val ownerId = uuid("owner_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object MessageSendEvents : UUIDTable("events_message_send") {
    val messageId = varchar("message_id", 255)
    val userId = uuid("user_id")
    val guildId = uuid("guild_id")
    val connectionName = varchar("connection_name", 255)
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object ConnectionCreateEvents : UUIDTable("events_connection_create") {
    val name = varchar("name", 200)
    val guildId = uuid("guild_id")
    val userId = uuid("user_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object ConnectionDeleteEvents : UUIDTable("events_connection_delete") {
    val name = varchar("name", 200)
    val guildId = uuid("guild_id")
    val userId = uuid("user_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}