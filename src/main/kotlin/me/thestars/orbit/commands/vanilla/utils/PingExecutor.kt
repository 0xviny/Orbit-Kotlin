package me.thestars.orbit.commands.vanilla.utils

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime

class PingExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        val getCurrentTime = Instant.now()
        val messageTime = context.event.timeCreated.toInstant()
        val messagePing = Duration.between(messageTime, getCurrentTime)

        context.reply {
            content = context.makeReply(
                "🏓",
                "Pong!\n" +
                        "Gateway ping: `${context.event.jda.gatewayPing}ms\n`" +
                        "Gateway message: `${messagePing.toMillis()}ms`"
            )
        }
    }
}