package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.Bumps
import me.thestars.orbit.database.table.Categories
import me.thestars.orbit.database.table.ConnectionInvites
import me.thestars.orbit.database.table.GiveawayParticipants
import me.thestars.orbit.database.table.Giveaways
import me.thestars.orbit.database.table.ModerationRules
import me.thestars.orbit.database.table.OrbitConnections
import me.thestars.orbit.database.table.Polls
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class OrbitConnection(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<OrbitConnection>(OrbitConnections)

    var name by OrbitConnections.name
    var icon by OrbitConnections.icon
    var description by OrbitConnections.description
    var invite by OrbitConnections.invite
    var pauseAt by OrbitConnections.pauseAt
    var lockedAt by OrbitConnections.lockedAt
    var creatorId by OrbitConnections.creatorId
    var createdAt by OrbitConnections.createdAt
    var type by OrbitConnections.type
    var channelId by OrbitConnections.channelId
    var logsChannelId by OrbitConnections.logsChannelId
    var enabledLogTypes by OrbitConnections.enabledLogTypes
    var flags by OrbitConnections.flags
    var messageComponentType by OrbitConnections.messageComponentType
    var componentBackground by OrbitConnections.componentBackground
    var guild by Guild referencedOn OrbitConnections.guild
    var lastBumpedAt by OrbitConnections.lastBumpedAt
    var language by OrbitConnections.language

    val moderationRules by ModerationRule referrersOn ModerationRules.connection
}

class ConnectionInvite(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ConnectionInvite>(ConnectionInvites)

    var code by ConnectionInvites.code
    var connection by OrbitConnection referencedOn ConnectionInvites.connection
    var createdAt by ConnectionInvites.createdAt
    var expiresAt by ConnectionInvites.expiresAt
    var maxUses by ConnectionInvites.maxUses
    var uses by ConnectionInvites.uses
    var createdBy by ConnectionInvites.createdBy
}

class ModerationRule(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ModerationRule>(ModerationRules)

    var connection by OrbitConnection referencedOn ModerationRules.connection
    var type by ModerationRules.type
    var pattern by ModerationRules.pattern
    var action by ModerationRules.action
    var reason by ModerationRules.reason
    var createdAt by ModerationRules.createdAt
}

class Category(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Category>(Categories)

    var name by Categories.name
    var slug by Categories.slug

    val connections by OrbitConnection via OrbitConnections // placeholder; use referrers or explicit join if needed
}


class Poll(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Poll>(Polls)

    var question by Polls.question
    var options by Polls.options
    var votes by Polls.votes
    var connectionName by Polls.connectionName
    var authorId by Polls.authorId
    var createdAt by Polls.createdAt
    var endsAt by Polls.endsAt
    var messageId by Polls.messageId
    var ended by Polls.ended
    var forwardedMessages by Polls.forwardedMessages
}

class Giveaway(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Giveaway>(Giveaways)

    var prize by Giveaways.prize
    var winnerCount by Giveaways.winnerCount
    var connectionName by Giveaways.connectionName
    var authorId by Giveaways.authorId
    var endsAt by Giveaways.endsAt
    var ended by Giveaways.ended
    var messageId by Giveaways.messageId
    var forwardedMessages by Giveaways.forwardedMessages

    val participants by GiveawayParticipant referrersOn GiveawayParticipants.giveaway
}

class GiveawayParticipant(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GiveawayParticipant>(GiveawayParticipants)

    var giveaway by Giveaway referencedOn GiveawayParticipants.giveaway
    var userId by GiveawayParticipants.userId
}


class Bump(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Bump>(Bumps)

    var connectionName by Bumps.connectionName
    var userId by Bumps.userId
    var createdAt by Bumps.createdAt
}