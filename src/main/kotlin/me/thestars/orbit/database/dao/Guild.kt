package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.AutoPings
import me.thestars.orbit.database.table.Guilds
import me.thestars.orbit.database.table.OrbitConnections
import me.thestars.orbit.database.table.Premiums
import me.thestars.orbit.database.table.UserGuildSettings
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class Guild(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Guild>(Guilds)

    var prefix by Guilds.prefix
    var cases by Guilds.cases
    var language by Guilds.language
    var createdAt by Guilds.createdAt

    val orbitConnections by OrbitConnection referrersOn OrbitConnections.guild
    val premium by Premium referrersOn Premiums.guild
}


class UserGuildSetting(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserGuildSetting>(UserGuildSettings)

    var userId by UserGuildSettings.userId
    var guildId by UserGuildSettings.guildId
    var atmBorder by UserGuildSettings.atmBorder
}

class AutoPing(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AutoPing>(AutoPings)

    var guildId by AutoPings.guildId
    var channelId by AutoPings.channelId
    var pingId by AutoPings.pingId
}