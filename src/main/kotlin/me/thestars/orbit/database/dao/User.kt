package me.thestars.orbit.database.dao

import me.thestars.orbit.database.table.AfkStatus
import me.thestars.orbit.database.table.Blacklist
import me.thestars.orbit.database.table.Boxes
import me.thestars.orbit.database.table.Cosmetics
import me.thestars.orbit.database.table.Economy
import me.thestars.orbit.database.table.MessageLikes
import me.thestars.orbit.database.table.Notifications
import me.thestars.orbit.database.table.Partners
import me.thestars.orbit.database.table.Premiums
import me.thestars.orbit.database.table.SavedMessages
import me.thestars.orbit.database.table.StaffMembers
import me.thestars.orbit.database.table.Testimonials
import me.thestars.orbit.database.table.UserCosmetics
import me.thestars.orbit.database.table.Users
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var allowMentions by Users.allowMentions
    var receiveNotifications by Users.receiveNotifications
    var about by Users.about
    var banner by Users.banner
    var border by Users.border
    var xp by Users.xp
    var level by Users.level
    var achievements by Users.achievements

    // relations (examples)
    val messageLikes by MessageLike referrersOn MessageLikes.user
    val savedMessages by SavedMessage referrersOn SavedMessages.user
}

class Premium(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Premium>(Premiums)

    var subscriptionId by Premiums.subscriptionId
    var activatedAt by Premiums.activatedAt
    var expiresAt by Premiums.expiresAt
    var renewsAt by Premiums.renewsAt
    var cancelsAt by Premiums.cancelsAt
    var userActiveId by Premiums.userActiveId
    var guild by Guild referencedOn Premiums.guild
}

class Cosmetic(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Cosmetic>(Cosmetics)

    var name by Cosmetics.name
    var value by Cosmetics.value
    var type by Cosmetics.type

    val owners by UserCosmetic referrersOn UserCosmetics.cosmeticId
}

class UserCosmetic(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserCosmetic>(UserCosmetics)

    var userId by UserCosmetics.userId
    var cosmeticId by UserCosmetics.cosmeticId

    val user by User referencedOn UserCosmetics.userId
    val cosmetic by Cosmetic referencedOn UserCosmetics.cosmeticId
}

class Staff(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Staff>(StaffMembers)

    var userId by StaffMembers.userId
}

class Partner(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Partner>(Partners)

    var userId by Partners.userId
}

class AfkStatusEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AfkStatusEntity>(AfkStatus)

    var reason by AfkStatus.reason
    var since by AfkStatus.since
    var mentions by AfkStatus.mentions
}

class BlacklistEntry(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BlacklistEntry>(Blacklist)

    var reason by Blacklist.reason
    var temp by Blacklist.temp
    var modId by Blacklist.modId
    var user by User referencedOn Blacklist.user
}

class Notification(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Notification>(Notifications)

    var alertType by Notifications.alertType
    var message by Notifications.message
    var timestamp by Notifications.timestamp
    var read by Notifications.read
    var user by User referencedOn Notifications.user
    var actorId by Notifications.actorId
    var originMessageId by Notifications.originMessageId
    var originChannelId by Notifications.originChannelId
    var originGuildId by Notifications.originGuildId
    var forwardedMessageId by Notifications.forwardedMessageId
    var forwardedChannelId by Notifications.forwardedChannelId
}

class Testimonial(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Testimonial>(Testimonials)

    var userId by Testimonials.userId
    var userName by Testimonials.userName
    var userAvatar by Testimonials.userAvatar
    var comment by Testimonials.comment
    var isApproved by Testimonials.isApproved
    var createdAt by Testimonials.createdAt
}

class EconomyEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EconomyEntity>(Economy)

    var userId by Economy.userId
    var balance by Economy.balance
    var bank by Economy.bank
    var dailyStreak by Economy.dailyStreak
    var lastDailyClaim by Economy.lastDailyClaim
    var voteStreak by Economy.voteStreak
    var lastVoteClaim by Economy.lastVoteClaim
    var voteReminderSent by Economy.voteReminderSent

    val boxes by Box referrersOn Boxes.user
}

class Box(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Box>(Boxes)

    var user by Economy referencedOn Boxes.user
    var type by Boxes.type
    var quantity by Boxes.quantity
    var createdAt by Boxes.createdAt
    var updatedAt by Boxes.updatedAt
}