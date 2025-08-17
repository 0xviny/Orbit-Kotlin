package me.thestars.orbit.database.table

import me.thestars.orbit.utils.Enums
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object Users : UUIDTable("users") {
    val allowMentions = bool("allow_mentions").default(true)
    val receiveNotifications = bool("receive_notifications").default(true)
    val about = text("about").nullable()
    val banner = varchar("banner", 255).nullable()
    val border = varchar("border", 100).nullable()
    val xp = integer("xp").default(0)
    val level = integer("level").default(1)
    val achievements = text("achievements").default("[]") // json array stored as text
}

object Premiums : UUIDTable("premium") {
    val subscriptionId = varchar("subscription_id", 255)
    val activatedAt = long("activated_at").clientDefault { System.currentTimeMillis() }
    val expiresAt = long("expires_at")
    val renewsAt = long("renews_at").nullable()
    val cancelsAt = long("cancels_at").nullable()
    val userActiveId = uuid("user_active_id")
    val guild = reference("guild_id", Guilds, onDelete = ReferenceOption.CASCADE).uniqueIndex()
}

object Cosmetics : UUIDTable("cosmetics") {
    val name = varchar("name", 150)
    val value = text("value")
    val type = enumerationByName("type", 20, Enums.CosmeticType::class)
}

object UserCosmetics : UUIDTable("user_cosmetics") {
    val userId = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val cosmeticId = reference("cosmetic_id", Cosmetics, onDelete = ReferenceOption.CASCADE)
    // unique index should be created in DB migration: (userId, cosmeticId)
}

object Partners : UUIDTable("partners") {
    val userId = uuid("user_id").uniqueIndex()
}

object StaffMembers : UUIDTable("staff_members") {
    val userId = uuid("user_id").uniqueIndex()
}


object AfkStatus : UUIDTable("afk_status") {
    val reason = varchar("reason", 500)
    val since = long("since").clientDefault { System.currentTimeMillis() }
    val mentions = text("mentions").default("[]")
}

object Blacklist : UUIDTable("blacklist") {
    val reason = varchar("reason", 500)
    val temp = long("temp").nullable()
    val modId = varchar("mod_id", 255)
    val user =
        reference("user_id", me.thestars.orbit.database.table.Users, onDelete = ReferenceOption.CASCADE).uniqueIndex()
}

object Notifications : UUIDTable("notifications") {
    val alertType = enumerationByName("alert_type", 20, me.thestars.orbit.utils.Enums.AlertType::class)
    val message = text("message")
    val timestamp = long("timestamp").clientDefault { System.currentTimeMillis() }
    val read = bool("read").default(false)
    val user = reference("user_id", me.thestars.orbit.database.table.Users, onDelete = ReferenceOption.CASCADE)
    val actorId = varchar("actor_id", 255).nullable()
    val originMessageId = varchar("origin_message_id", 255).nullable()
    val originChannelId = varchar("origin_channel_id", 255).nullable()
    val originGuildId = varchar("origin_guild_id", 255).nullable()
    val forwardedMessageId = varchar("forwarded_message_id", 255).nullable()
    val forwardedChannelId = varchar("forwarded_channel_id", 255).nullable()
}

object Testimonials : UUIDTable("testimonials") {
    val userId = uuid("user_id")
    val userName = varchar("user_name", 200)
    val userAvatar = varchar("user_avatar", 255).nullable()
    val comment = text("comment")
    val isApproved = bool("is_approved").default(false)
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}

object Economy : UUIDTable("economy") {
    val userId = uuid("user_id").uniqueIndex()
    val balance = integer("balance").default(0)
    val bank = integer("bank").default(0)
    val dailyStreak = integer("daily_streak").default(0)
    val lastDailyClaim = long("last_daily_claim").nullable()
    val voteStreak = integer("vote_streak").default(0)
    val lastVoteClaim = long("last_vote_claim").nullable()
    val voteReminderSent = bool("vote_reminder_sent").default(false)
}

object Boxes : UUIDTable("boxes") {
    val user = reference("user_id", Economy, onDelete = ReferenceOption.CASCADE)
    val type = varchar("type", 100)
    val quantity = integer("quantity")
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
    val updatedAt = long("updated_at").clientDefault { System.currentTimeMillis() }
}