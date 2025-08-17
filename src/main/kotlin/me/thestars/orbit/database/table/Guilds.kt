package me.thestars.orbit.database.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Guilds : UUIDTable("guilds") {
    val prefix = varchar("prefix", 10).default("!")
    val cases = text("cases").nullable()
    val language = varchar("language", 10).default("pt-BR")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object UserGuildSettings : UUIDTable("user_guild_settings") {
    val userId = uuid("user_id")
    val guildId = uuid("guild_id")
    val atmBorder = varchar("atm_border", 100).default("gold")
}


object AutoPings : UUIDTable("auto_pings") {
    val guildId = uuid("guild_id")
    val channelId = varchar("channel_id", 255).uniqueIndex()
    val pingId = varchar("ping_id", 255)
}