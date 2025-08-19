package me.thestars.orbit.database.utils

import me.thestars.orbit.utils.Enums
import me.thestars.orbit.database.dao.AfkStatusEntity
import me.thestars.orbit.database.dao.AutoPing
import me.thestars.orbit.database.dao.BlacklistEntry
import me.thestars.orbit.database.dao.Box
import me.thestars.orbit.database.dao.CommandUsedEvent
import me.thestars.orbit.database.dao.ConnectionCreateEvent
import me.thestars.orbit.database.dao.ConnectionDeleteEvent
import me.thestars.orbit.database.dao.ConnectionInvite
import me.thestars.orbit.database.dao.Cosmetic
import me.thestars.orbit.database.dao.EconomyEntity
import me.thestars.orbit.database.dao.ForwardMessage
import me.thestars.orbit.database.dao.Giveaway
import me.thestars.orbit.database.dao.GiveawayParticipant
import me.thestars.orbit.database.dao.Guild
import me.thestars.orbit.database.dao.GuildCreateEvent
import me.thestars.orbit.database.dao.GuildDeleteEvent
import me.thestars.orbit.database.dao.Message
import me.thestars.orbit.database.dao.MessageLike
import me.thestars.orbit.database.dao.MessageSendEvent
import me.thestars.orbit.database.dao.ModerationRule
import me.thestars.orbit.database.dao.Premium
import me.thestars.orbit.database.dao.SavedMessage
import me.thestars.orbit.database.dao.Testimonial
import me.thestars.orbit.database.dao.User
import me.thestars.orbit.database.dao.UserCosmetic
import me.thestars.orbit.database.dao.UserGuildSetting
import me.thestars.orbit.database.dao.OrbitConnection
import me.thestars.orbit.database.dao.Bump
import me.thestars.orbit.database.dao.Notification
import me.thestars.orbit.database.table.AutoPings
import me.thestars.orbit.database.table.ConnectionInvites
import me.thestars.orbit.database.table.Economy
import me.thestars.orbit.database.table.GiveawayParticipants
import me.thestars.orbit.database.table.MessageLikes
import me.thestars.orbit.database.table.Notifications
import me.thestars.orbit.database.table.SavedMessages
import me.thestars.orbit.database.table.UserCosmetics
import me.thestars.orbit.database.table.OrbitConnections
import me.thestars.orbit.database.table.UserGuildSettings
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.random.Random

suspend fun OrbitConnection.Companion.findByName(name: String, channelId: String): OrbitConnection? {
    return newSuspendedTransaction {
        OrbitConnection.find { (OrbitConnections.name eq name) and (OrbitConnections.channelId eq channelId) }.firstOrNull()
    }
}

suspend fun OrbitConnection.Companion.existsConnection(name: String): OrbitConnection? {
    return newSuspendedTransaction {
        OrbitConnection.find { OrbitConnections.name eq name }.firstOrNull()
    }
}

suspend fun OrbitConnection.Companion.findByIdOrNull(id: UUID): OrbitConnection? {
    return newSuspendedTransaction {
        OrbitConnection.findById(id)
    }
}

suspend fun OrbitConnection.Companion.create(
    name: String,
    guild: Guild,
    creatorId: String,
    description: String? = null,
    invite: String? = null,
    channelId: String? = null,
    logsChannelId: String? = null,
    type: Int? = null,
    flags: Int? = null,
    language: String? = null,
    messageComponentType: Int? = null,
    componentBackground: String? = null
): OrbitConnection {
    return newSuspendedTransaction {
        OrbitConnection.new {
            this.name = name
            this.guild = guild
            this.creatorId = creatorId
            if (description != null) this.description = description
            if (invite != null) this.invite = invite
            if (channelId != null) this.channelId = channelId
            if (logsChannelId != null) this.logsChannelId = logsChannelId
            if (type != null) this.type = type
            if (language != null) this.language = language
            if (messageComponentType != null) this.messageComponentType = messageComponentType
            if (componentBackground != null) this.componentBackground = componentBackground
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun OrbitConnection.updateFields(
    name: String? = null,
    description: String? = null,
    invite: String? = null,
    channelId: String? = null,
    logsChannelId: String? = null,
    pauseAt: Long? = null,
    lockedAt: Long? = null,
    language: String? = null,
    enabledLogTypes: String? = null,
    flags: Int? = null
): OrbitConnection {
    val self = this
    return newSuspendedTransaction {
        if (name != null) self.name = name
        if (description != null) self.description = description
        if (invite != null) self.invite = invite
        if (channelId != null) self.channelId = channelId
        if (logsChannelId != null) self.logsChannelId = logsChannelId
        if (pauseAt != null) self.pauseAt = pauseAt
        if (lockedAt != null) self.lockedAt = lockedAt
        if (language != null) self.language = language
        if (enabledLogTypes != null) self.enabledLogTypes = enabledLogTypes
        if (flags != null) self.flags = flags
        self
    }
}

suspend fun OrbitConnection.addModerationRule(
    type: Enums.ModerationRuleType,
    pattern: String,
    action: Enums.ModerationAction,
    reason: String? = null
): ModerationRule {
    val self = this
    return newSuspendedTransaction {
        ModerationRule.new {
            this.connection = self
            this.type = type
            this.pattern = pattern
            this.action = action
            if (reason != null) this.reason = reason
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun ModerationRule.deleteRule() {
    val self = this
    newSuspendedTransaction {
        self.delete()
    }
}

suspend fun ConnectionInvite.Companion.createInvite(
    connection: OrbitConnection,
    code: String,
    createdBy: UUID,
    expiresAt: Long? = null,
    maxUses: Int? = null
): ConnectionInvite {
    return newSuspendedTransaction {
        ConnectionInvite.new {
            this.connection = connection
            this.code = code
            this.createdBy = createdBy
            if (expiresAt != null) this.expiresAt = expiresAt
            if (maxUses != null) this.maxUses = maxUses
            this.createdAt = System.currentTimeMillis()
            this.uses = 0
        }
    }
}

suspend fun ConnectionInvite.findByCode(code: String): ConnectionInvite? {
    return newSuspendedTransaction {
        ConnectionInvite.find { ConnectionInvites.code eq code }.firstOrNull()
    }
}

suspend fun Message.addLike(user: User): MessageLike {
    val messageSelf = this
    return newSuspendedTransaction {
        MessageLike.new {
            this.user = user
            this.message = messageSelf
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun Message.removeLike(user: User) {
    val messageSelf = this
    newSuspendedTransaction {
        MessageLike.find { (MessageLikes.message eq messageSelf.id) and (MessageLikes.user eq user.id) }
            .forEach { it.delete() }
    }
}

suspend fun Message.getLikeCount(): Int {
    val messageSelf = this
    return newSuspendedTransaction {
        MessageLike.find { MessageLikes.message eq messageSelf.id }.count().toInt()
    }
}

suspend fun Message.isLikedBy(user: User): Boolean {
    val messageSelf = this
    return newSuspendedTransaction {
        MessageLike.find { (MessageLikes.message eq messageSelf.id) and (MessageLikes.user eq user.id) }.any()
    }
}

suspend fun Message.saveForUser(user: User): SavedMessage {
    val messageSelf = this
    return newSuspendedTransaction {
        SavedMessage.new {
            this.user = user
            this.message = messageSelf
            this.savedAt = System.currentTimeMillis()
        }
    }
}

suspend fun Message.unsaveForUser(user: User) {
    val messageSelf = this
    newSuspendedTransaction {
        SavedMessage.find { (SavedMessages.message eq messageSelf.id) and (SavedMessages.user eq user.id) }
            .forEach { it.delete() }
    }
}

suspend fun SavedMessage.exists(message: Message, user: User): Boolean {
    return newSuspendedTransaction {
        SavedMessage.find { (SavedMessages.message eq message.id) and (SavedMessages.user eq user.id) }.any()
    }
}

suspend fun User.addXp(amount: Int): User {
    val self = this
    return newSuspendedTransaction {
        self.xp = (self.xp ?: 0) + amount
        self
    }
}

suspend fun User.getSavedMessages(): List<Message> {
    val self = this
    return newSuspendedTransaction {
        self.savedMessages.map { it.message }
    }
}

suspend fun Giveaway.addParticipant(userId: UUID): GiveawayParticipant {
    val giveawaySelf = this
    return newSuspendedTransaction {
        GiveawayParticipant.new {
            this.giveaway = giveawaySelf
            this.userId = userId
        }
    }
}

suspend fun Giveaway.getParticipantIds(): List<UUID> {
    val self = this
    return newSuspendedTransaction {
        self.participants.map { it.userId }
    }
}

suspend fun Giveaway.pickWinners(winnerCount: Int): List<UUID> {
    val self = this
    return newSuspendedTransaction {
        val ids = self.participants.map { it.userId }.toMutableList()
        if (ids.isEmpty() || winnerCount <= 0) return@newSuspendedTransaction emptyList()
        val winners = mutableListOf<UUID>()
        repeat(minOf(winnerCount, ids.size)) {
            val idx = Random.nextInt(ids.size)
            winners.add(ids.removeAt(idx))
        }
        winners
    }
}

suspend fun GiveawayParticipant.exists(giveaway: Giveaway, userId: UUID): Boolean {
    return newSuspendedTransaction {
        GiveawayParticipant.find { (GiveawayParticipants.giveaway eq giveaway.id) and (GiveawayParticipants.userId eq userId) }
            .any()
    }
}

suspend fun Guild.getConnections(): List<OrbitConnection> {
    val self = this
    return newSuspendedTransaction {
        self.orbitConnections.toList()
    }
}

suspend fun Guild.Companion.createOrGet(guildId: Long): Guild {
    return newSuspendedTransaction {
        Guild.findById(guildId) ?: Guild.new(guildId) {
        }
    }
}

suspend fun Guild.getPremium(): Premium? {
    val self = this
    return newSuspendedTransaction {
        self.premium.firstOrNull()
    }
}

suspend fun Guild.Companion.createOrbitConnection(
    name: String,
    creatorId: String,
    channelId: String,
    type: Int,
    flags: Int,
    guild: Guild
): OrbitConnection {
    return OrbitConnection.create(
        name = name,
        guild = guild,
        creatorId = creatorId,
        channelId = channelId,
        flags = flags,
        type = type
    )
}

suspend fun UserGuildSetting.Companion.findFor(userId: UUID, guildId: UUID): UserGuildSetting? {
    return newSuspendedTransaction {
        UserGuildSetting.find { (UserGuildSettings.userId eq userId) and (UserGuildSettings.guildId eq guildId) }
            .firstOrNull()
    }
}

suspend fun UserGuildSetting.Companion.createOrUpdate(
    userId: UUID,
    guildId: UUID,
    atmBorder: String?
): UserGuildSetting {
    return newSuspendedTransaction {
        val existing =
            UserGuildSetting.find { (UserGuildSettings.userId eq userId) and (UserGuildSettings.guildId eq guildId) }
                .firstOrNull()
        if (existing != null) {
            if (atmBorder != null) existing.atmBorder = atmBorder
            existing
        } else {
            UserGuildSetting.new {
                this.userId = userId
                this.guildId = guildId
                this.atmBorder = atmBorder ?: "gold"
            }
        }
    }
}

suspend fun AutoPing.Companion.create(guildId: UUID, channelId: String, pingId: String): AutoPing {
    return newSuspendedTransaction {
        AutoPing.new {
            this.guildId = guildId
            this.channelId = channelId
            this.pingId = pingId
        }
    }
}

suspend fun AutoPing.Companion.findByGuild(guildId: UUID): List<AutoPing> {
    return newSuspendedTransaction {
        AutoPing.find { AutoPings.guildId eq guildId }.toList()
    }
}

suspend fun ForwardMessage.Companion.createForward(
    originMessageId: String,
    originChannelId: String,
    forwardedMessageId: String,
    forwardedChannelId: String,
    connectionName: String,
    originalAuthorId: String?,
    webhookId: String?,
    webhookToken: String?
): ForwardMessage {
    return newSuspendedTransaction {
        ForwardMessage.new {
            this.originMessageId = originMessageId
            this.originChannelId = originChannelId
            this.forwardedMessageId = forwardedMessageId
            this.forwardedChannelId = forwardedChannelId
            this.connectionName = connectionName
            if (originalAuthorId != null) this.originalAuthorId = originalAuthorId
            if (webhookId != null) this.webhookId = webhookId
            if (webhookToken != null) this.webhookToken = webhookToken
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun Bump.Companion.create(connectionName: String, userId: UUID): Bump {
    return newSuspendedTransaction {
        Bump.new {
            this.connectionName = connectionName
            this.userId = userId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun CommandUsedEvent.Companion.log(
    name: String,
    guildName: String?,
    guildId: UUID?,
    userId: UUID
): CommandUsedEvent {
    return newSuspendedTransaction {
        CommandUsedEvent.new {
            this.name = name
            if (guildName != null) this.guildName = guildName else this.guildName = ""
            if (guildId != null) this.guildId = guildId
            this.userId = userId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun GuildCreateEvent.Companion.log(name: String, guildId: UUID, ownerId: UUID): GuildCreateEvent {
    return newSuspendedTransaction {
        GuildCreateEvent.new {
            this.name = name
            this.guildId = guildId
            this.ownerId = ownerId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun GuildDeleteEvent.Companion.log(name: String, guildId: UUID, ownerId: UUID): GuildDeleteEvent {
    return newSuspendedTransaction {
        GuildDeleteEvent.new {
            this.name = name
            this.guildId = guildId
            this.ownerId = ownerId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun MessageSendEvent.Companion.log(
    messageId: String,
    userId: UUID,
    guildId: UUID,
    connectionName: String
): MessageSendEvent {
    return newSuspendedTransaction {
        MessageSendEvent.new {
            this.messageId = messageId
            this.userId = userId
            this.guildId = guildId
            this.connectionName = connectionName
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun ConnectionCreateEvent.Companion.log(name: String, guildId: UUID, userId: UUID): ConnectionCreateEvent {
    return newSuspendedTransaction {
        ConnectionCreateEvent.new {
            this.name = name
            this.guildId = guildId
            this.userId = userId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun ConnectionDeleteEvent.Companion.log(name: String, guildId: UUID, userId: UUID): ConnectionDeleteEvent {
    return newSuspendedTransaction {
        ConnectionDeleteEvent.new {
            this.name = name
            this.guildId = guildId
            this.userId = userId
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun Premium.Companion.createForGuild(
    guild: Guild,
    subscriptionId: String,
    activatedAt: Long?,
    expiresAt: Long,
    userActiveId: UUID?
): Premium {
    return newSuspendedTransaction {
        Premium.new {
            this.guild = guild
            this.subscriptionId = subscriptionId
            if (activatedAt != null) this.activatedAt = activatedAt
            this.expiresAt = expiresAt
            this.renewsAt = null
            this.cancelsAt = null
            if (userActiveId != null) this.userActiveId = userActiveId
        }
    }
}

suspend fun Premium.cancel(): Premium {
    val self = this
    return newSuspendedTransaction {
        self.cancelsAt = System.currentTimeMillis()
        self
    }
}

suspend fun Cosmetic.Companion.create(name: String, value: String, type: Enums.CosmeticType): Cosmetic {
    return newSuspendedTransaction {
        Cosmetic.new {
            this.name = name
            this.value = value
            this.type = type
        }
    }
}

suspend fun Cosmetic.assignToUser(user: User): UserCosmetic {
    return newSuspendedTransaction {
        UserCosmetic.new {
            this.userId = user.id
            this.cosmeticId = this@assignToUser.id
        }
    }
}

suspend fun User.getCosmetics(): List<Cosmetic> {
    val self = this
    return newSuspendedTransaction {
        UserCosmetic.find { UserCosmetics.userId eq self.id.value }.map { it.cosmetic }
    }
}

suspend fun Notification.createForUser(
    user: User,
    alertType: Enums.AlertType,
    message: String?,
    actorId: String?,
    originMessageId: String?,
    originChannelId: String?,
    originGuildId: String?,
    forwardedMessageId: String?,
    forwardedChannelId: String?
): Notification {
    return newSuspendedTransaction {
        Notification.new {
            this.user = user
            this.alertType = alertType
            if (message != null) this.message = message
            this.timestamp = System.currentTimeMillis()
            this.read = false
            if (actorId != null) this.actorId = actorId
            if (originMessageId != null) this.originMessageId = originMessageId
            if (originChannelId != null) this.originChannelId = originChannelId
            if (originGuildId != null) this.originGuildId = originGuildId
            if (forwardedMessageId != null) this.forwardedMessageId = forwardedMessageId
            if (forwardedChannelId != null) this.forwardedChannelId = forwardedChannelId
        }
    }
}

suspend fun Notification.getForUser(user: User): List<Notification> {
    return newSuspendedTransaction {
        Notification.find { Notifications.user eq user.id }.toList()
    }
}

suspend fun BlacklistEntry.Companion.add(user: User, reason: String?, temp: Long?, modId: String): BlacklistEntry {
    return newSuspendedTransaction {
        BlacklistEntry.new {
            this.user = user
            if (reason != null) this.reason = reason
            this.temp = temp
            this.modId = modId
        }
    }
}

suspend fun BlacklistEntry.removeEntry() {
    val self = this
    newSuspendedTransaction {
        self.delete()
    }
}

suspend fun AfkStatusEntity.Companion.setAfk(reason: String?, mentions: List<String>?): AfkStatusEntity {
    return newSuspendedTransaction {
        AfkStatusEntity.new {
            if (reason != null) this.reason = reason else this.reason = ""
            this.since = System.currentTimeMillis()
            this.mentions = mentions?.joinToString(",") ?: "[]"
        }
    }
}

suspend fun Testimonial.Companion.create(
    userId: UUID,
    userName: String,
    userAvatar: String?,
    comment: String,
    isApproved: Boolean
): Testimonial {
    return newSuspendedTransaction {
        Testimonial.new {
            this.userId = userId
            this.userName = userName
            if (userAvatar != null) this.userAvatar = userAvatar
            this.comment = comment
            this.isApproved = isApproved
            this.createdAt = System.currentTimeMillis()
        }
    }
}

suspend fun EconomyEntity.Companion.findByUserId(userId: UUID): EconomyEntity? {
    return newSuspendedTransaction {
        EconomyEntity.find { Economy.userId eq userId }.firstOrNull()
    }
}

suspend fun EconomyEntity.createOrGet(userId: UUID): EconomyEntity {
    return newSuspendedTransaction {
        EconomyEntity.find { Economy.userId eq userId }.firstOrNull() ?: EconomyEntity.new {
            this.userId = userId
            this.balance = 0
            this.bank = 0
            this.dailyStreak = 0
            this.lastDailyClaim = null
            this.voteStreak = 0
            this.lastVoteClaim = null
            this.voteReminderSent = false
        }
    }
}

suspend fun EconomyEntity.addBalance(amount: Int): EconomyEntity {
    val self = this
    return newSuspendedTransaction {
        self.balance = (self.balance ?: 0) + amount
        self
    }
}

suspend fun Box.Companion.createForUser(user: EconomyEntity, type: String, quantity: Int): Box {
    return newSuspendedTransaction {
        Box.new {
            this.user = user
            this.type = type
            this.quantity = quantity
            this.createdAt = System.currentTimeMillis()
            this.updatedAt = System.currentTimeMillis()
        }
    }
}
