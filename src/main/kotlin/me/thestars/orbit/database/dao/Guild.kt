package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.Guilds
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Guild(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Guild>(Guilds) {
        fun getOrInsert(id: Long) = Guild.findById(id) ?: Guild.new(id) {}
    }

    val guildId = this.id.value
    val prefix by Guilds.prefix
}