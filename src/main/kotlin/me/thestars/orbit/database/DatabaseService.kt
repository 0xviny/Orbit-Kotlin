package me.thestars.orbit.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.thestars.orbit.database.table.AfkStatus
import me.thestars.orbit.database.table.AutoPings
import me.thestars.orbit.database.table.Blacklist
import me.thestars.orbit.database.table.Boxes
import me.thestars.orbit.database.table.Bumps
import me.thestars.orbit.database.table.Categories
import me.thestars.orbit.database.table.CommandUsedEvents
import me.thestars.orbit.database.table.ConnectionCreateEvents
import me.thestars.orbit.database.table.ConnectionDeleteEvents
import me.thestars.orbit.database.table.ConnectionInvites
import me.thestars.orbit.database.table.Cosmetics
import me.thestars.orbit.database.table.Economy
import me.thestars.orbit.database.table.ForwardMessages
import me.thestars.orbit.database.table.GiveawayParticipants
import me.thestars.orbit.database.table.Giveaways
import me.thestars.orbit.database.table.GuildCreateEvents
import me.thestars.orbit.database.table.GuildDeleteEvents
import me.thestars.orbit.database.table.Guilds
import me.thestars.orbit.database.table.MessageLikes
import me.thestars.orbit.database.table.MessageSendEvents
import me.thestars.orbit.database.table.Messages
import me.thestars.orbit.database.table.ModerationRules
import me.thestars.orbit.database.table.Notifications
import me.thestars.orbit.database.table.OrbitConnections
import me.thestars.orbit.database.table.Partners
import me.thestars.orbit.database.table.Polls
import me.thestars.orbit.database.table.Premiums
import me.thestars.orbit.database.table.SavedMessages
import me.thestars.orbit.database.table.StaffMembers
import me.thestars.orbit.database.table.Testimonials
import me.thestars.orbit.database.table.UserCosmetics
import me.thestars.orbit.database.table.UserGuildSettings
import me.thestars.orbit.database.table.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

data class PostgresConfig(
    val url: String, val username: String, val password: String
)

object DatabaseService {
    private var hikari: HikariDataSource? = null

    fun connect(postgres: PostgresConfig) {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:${postgres.url}"
            driverClassName = "org.postgresql.Driver"
            username = postgres.username
            password = postgres.password
            maximumPoolSize = 8
        }

        hikari = HikariDataSource(config)
        Database.connect(hikari!!)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Guilds,
                Users,
                Economy,
                UserGuildSettings,
                AutoPings,
                Premiums,
                Cosmetics,
                UserCosmetics,
                Partners,
                StaffMembers,
                AfkStatus,
                Blacklist,
                Notifications,
                Testimonials,
                Boxes,
                OrbitConnections,
                ConnectionInvites,
                ModerationRules,
                Categories,
                Polls,
                Giveaways,
                GiveawayParticipants,
                Bumps,
                Messages,
                MessageLikes,
                SavedMessages,
                ForwardMessages,
                CommandUsedEvents,
                GuildCreateEvents,
                GuildDeleteEvents,
                MessageSendEvents,
                ConnectionCreateEvents,
                ConnectionDeleteEvents
            )
        }
    }

    fun close() {
        hikari?.close()
        hikari = null
    }
}
