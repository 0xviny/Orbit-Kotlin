package me.thestars.orbit.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Guilds : LongIdTable() {
    var prefix = text("prefix").default("-c")
}