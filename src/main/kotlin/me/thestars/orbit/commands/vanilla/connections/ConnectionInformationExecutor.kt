package me.thestars.orbit.commands.vanilla.connections

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import me.thestars.orbit.database.dao.OrbitConnection
import me.thestars.orbit.database.utils.findByName
import me.thestars.orbit.database.utils.findConnectionsByChannel
import me.thestars.orbit.commands.vanilla.connections.declarations.ConnectionCommand.Companion.LOCALE_PREFIX

class ConnectionInformationExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        val connectionName = context.event.getOption("connection")!!.asString
        var connectionData;

        if (connectionName != null) {
            connectionData = OrbitConnection.findByName(connectionName, context.event.channelId!!)
        } else {
            val connectionInChannel = OrbitConnection.findConnectionsByChannel(context.event.channelId!!)

            if (connectionInChannel.isEmpty()) {
                context.reply {
                    content = context.makeReply(
                        "❌",
                        context.locale["$LOCALE_PREFIX.connections.error.errorChannelIsEmpty", context.event.user.asMention]
                    )
                }

                return
            }
        }
    }
}