package me.thestars.orbit.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.thestars.orbit.database.table.Guilds
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

data class PostgresConfig(
    val url: String,
    val username: String,
    val password: String
)

object DatabaseService {
    fun connect(postgres: PostgresConfig) {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:${postgres.url}"
            driverClassName = "org.postgresql.Driver"
            username = postgres.username
            password = postgres.password
            maximumPoolSize = 8
        }

        Database.connect(
            HikariDataSource(config)
        )

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Guilds)
        }
    }
}