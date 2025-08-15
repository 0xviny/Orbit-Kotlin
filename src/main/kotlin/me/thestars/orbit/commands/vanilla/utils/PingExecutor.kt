package me.thestars.orbit.commands.vanilla.utils

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor

class PingExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        context.reply {
            content = "Pong!\n" +
                    "Gateway ping: `${context.event.jda.gatewayPing}ms`"
        }
    }
}