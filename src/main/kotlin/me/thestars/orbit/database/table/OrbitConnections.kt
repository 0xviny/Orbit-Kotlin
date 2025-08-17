package me.thestars.orbit.database.table

import me.thestars.orbit.utils.Enums
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object OrbitConnections : UUIDTable("orbit_connections") {
    val name = varchar("name", 200)
    val icon = varchar("icon", 255).nullable()
    val description = text("description").nullable()
    val invite = varchar("invite", 255).nullable()
    val pauseAt = long("pause_at").nullable()
    val lockedAt = long("locked_at").nullable()
    val creatorId = uuid("creator_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
    val type = integer("type").nullable()
    val channelId = varchar("channel_id", 50)
    val logsChannelId = varchar("logs_channel_id", 50).nullable()
    val enabledLogTypes = text("enabled_log_types").default("[]")
    val flags = integer("flags").default(0)
    val messageComponentType = integer("message_component_type").default(1)
    val componentBackground = varchar("component_background", 255).nullable()
    val guild = reference("guild_id", Guilds, onDelete = ReferenceOption.CASCADE)
    val lastBumpedAt = long("last_bumped_at").nullable()
    val language = varchar("language", 10).default("pt-BR")

    val embedTitle = varchar("embed_title", 255).nullable()
    val embedDescription = text("embed_description").nullable()
    val embedColor = varchar("embed_color", 20).nullable()
    val webhookUsername = varchar("webhook_username", 80).nullable()
    val webhookAvatar = varchar("webhook_avatar", 255).nullable()
}


object ConnectionInvites : UUIDTable("connection_invites") {
    val code = varchar("code", 255).uniqueIndex()
    val connection = reference("connection_id", OrbitConnections, onDelete = ReferenceOption.CASCADE)
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
    val expiresAt = long("expires_at").nullable()
    val maxUses = integer("max_uses").nullable()
    val uses = integer("uses").default(0)
    val createdBy = uuid("created_by")
}

object ModerationRules : UUIDTable("moderation_rules") {
    val connection = reference("connection_id", OrbitConnections, onDelete = ReferenceOption.CASCADE)
    val type = enumerationByName("type", 20, Enums.ModerationRuleType::class)
    val pattern = text("pattern")
    val action =
        enumerationByName("action", 30, Enums.ModerationAction::class).default(Enums.ModerationAction.DELETE_MESSAGE)
    val reason = text("reason").nullable()
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object Categories : UUIDTable("categories") {
    val name = varchar("name", 200).uniqueIndex()
    val slug = varchar("slug", 200).uniqueIndex()
}


object Polls : UUIDTable("polls") {
    val question = text("question")
    val options = text("options") // json array of strings
    val votes = text("votes") // json array of ints
    val connectionName = varchar("connection_name", 200)
    val authorId = uuid("author_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
    val endsAt = long("ends_at").nullable()
    val messageId = varchar("message_id", 255)
    val ended = bool("ended").default(false)
    val forwardedMessages = text("forwarded_messages").nullable()
}

object Giveaways : UUIDTable("giveaways") {
    val prize = varchar("prize", 400)
    val winnerCount = integer("winner_count")
    val connectionName = varchar("connection_name", 200)
    val authorId = uuid("author_id")
    val endsAt = long("ends_at")
    val ended = bool("ended").default(false)
    val messageId = varchar("message_id", 255)
    val forwardedMessages = text("forwarded_messages").nullable()
}

object GiveawayParticipants : UUIDTable("giveaway_participants") {
    val giveaway = reference("giveaway_id", Giveaways, onDelete = ReferenceOption.CASCADE)
    val userId = uuid("user_id")
    // unique index (giveaway_id, user_id) in migration
}

object Bumps : UUIDTable("bumps") {
    val connectionName = varchar("connection_name", 255)
    val userId = uuid("user_id")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}