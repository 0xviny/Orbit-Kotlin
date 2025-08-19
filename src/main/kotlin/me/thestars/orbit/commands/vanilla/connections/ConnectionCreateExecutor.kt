package me.thestars.orbit.commands.vanilla.connections

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import me.thestars.orbit.commands.vanilla.connections.declarations.ConnectionCommand.Companion.LOCALE_PREFIX

class ConnectionCreateExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        if (!context.event.isFromGuild) {
            context.reply(ephemeral = true) {
                content = context.makeReply(
                    "❌",
                    context.locale["$LOCALE_PREFIX.connections.error.errorNotGuild", context.event.user.asMention]
                )

            }

            return
        }

        context.reply {
            content = context.makeReply(
                "📡",
                context.locale["$LOCALE_PREFIX.connections.create", context.event.user.asMention, context.event.guild!!.id]
            )
        }
    }
}