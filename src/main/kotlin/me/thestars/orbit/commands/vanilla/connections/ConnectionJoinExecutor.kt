package me.thestars.orbit.commands.vanilla.connections

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import me.thestars.orbit.commands.vanilla.connections.declarations.ConnectionCommand.Companion.LOCALE_PREFIX
import me.thestars.orbit.database.dao.Guild
import me.thestars.orbit.database.dao.OrbitConnection
import me.thestars.orbit.database.utils.createOrGet
import me.thestars.orbit.database.utils.createOrbitConnection
import me.thestars.orbit.database.utils.existsConnection
import me.thestars.orbit.database.utils.findByName
import net.dv8tion.jda.api.Permission
import me.thestars.orbit.utils.OrbitConnectionFlag

class ConnectionJoinExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        val connectionName = context.event.getOption("connection")!!.asString
        val targetChannel = context.event.getOption("connection_channel")!!.asChannel

        if (!context.event.interaction.isFromGuild) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorNotGuild", context.event.user.asMention]
                )

            }

            return
        }

        val userMemberPermissions = context.event.member!!.hasPermission(Permission.ADMINISTRATOR) || context.event.member!!.hasPermission(Permission.MANAGE_GUILD_EXPRESSIONS)
        if (!userMemberPermissions) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorNotPermission", context.event.user.asMention]
                )
            }

            return
        }

        val guildData = Guild.createOrGet(context.event.guild!!.idLong)
        val connectionInChannel = OrbitConnection.findByName(connectionName, context.event.channelId!!) ?: null

        if (connectionInChannel != null) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorConnectionAsExist", context.event.user.asMention, connectionName]
                )
            }

            return
        }

        val totalConnectionInGuild = guildData?.orbitConnections?.toList()?.size ?: 0
        val isPremium = guildData?.premium?.firstOrNull()?.expiresAt?.let { it > System.currentTimeMillis() } ?: false
        val limitConnectionsInGuild = if (isPremium) 15 else 5

        if (totalConnectionInGuild >= limitConnectionsInGuild) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorLimitConnectionsInGuild", context.event.user.asMention, limitConnectionsInGuild.toString()]
                )
            }

            return
        }

        val existsConnection = OrbitConnection.existsConnection(connectionName)

        if (existsConnection == null) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorConnectionNotExists", context.event.user.asMention, connectionName]
                )
            }

            return
        }

        Guild.createOrbitConnection(
            name = connectionName,
            guild = guildData,
            creatorId = context.event.user.id,
            type = 1,
            channelId = targetChannel.id,
            flags = combineFlags(
                OrbitConnectionFlag.AllowEmojis,
                OrbitConnectionFlag.AllowOrigin,
                OrbitConnectionFlag.AllowMentions,
                OrbitConnectionFlag.AllowFiles
            )
        )

        context.reply {
            content = context.makeReply(
                "✅",
                context.locale["$LOCALE_PREFIX.connections.success.successConnected", context.event.user.asMention, connectionName, targetChannel.asMention]
            )
        }
    }

    fun combineFlags(vararg flags: OrbitConnectionFlag): Int {
        return flags.fold(0) { acc, flag -> acc or flag.value }
    }
}