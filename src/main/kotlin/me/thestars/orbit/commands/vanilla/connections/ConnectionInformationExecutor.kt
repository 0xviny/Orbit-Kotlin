package me.thestars.orbit.commands.vanilla.connections

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import me.thestars.orbit.database.dao.OrbitConnection
import me.thestars.orbit.database.utils.findByName
import me.thestars.orbit.database.utils.findConnectionsByChannel
import me.thestars.orbit.commands.vanilla.connections.declarations.ConnectionCommand.Companion.LOCALE_PREFIX
import me.thestars.orbit.database.utils.countConnectionsByName

class ConnectionInformationExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        val connectionName = context.event.getOption("connection")!!.asString

        val connectionData = connectionName?.let {
            OrbitConnection.findByName(it, context.event.channelId!!)
        } ?: run {
            val connectionsInChannel = OrbitConnection.findConnectionsByChannel(context.event.channelId!!)

            when {
                connectionsInChannel.isEmpty() -> {
                    context.reply {
                        content = context.makeReply(
                            "❌",
                            context.locale["$LOCALE_PREFIX.connections.error.errorChannelIsEmpty", context.event.user.asMention]
                        )
                    }
                    return
                }

                connectionsInChannel.size > 1 -> {
                    context.reply {
                        content = context.makeReply(
                            "❌",
                            context.locale["$LOCALE_PREFIX.connections.error.errorMoreOfOneConnectionChannel", context.event.user.asMention]
                        )
                    }
                    return
                }

                else -> connectionsInChannel.first()
            }
        }

            if (connectionData == null) {
                context.reply {
                    content = context.makeReply(
                        "❌",
                        context.locale["$LOCALE_PREFIX.connections.error.errorConnectionNotFound", context.event.user.asMention, connectionName]
                    )
                }
            }

        val creator = try {
            context.jda.getUserById(connectionData.creatorId)
        } catch (e: Exception) {
            null
        }

        val connectedChannels = OrbitConnection.countConnectionsByName(connectionName)


    }
}