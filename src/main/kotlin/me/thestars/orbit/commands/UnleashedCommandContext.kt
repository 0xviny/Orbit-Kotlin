package me.thestars.orbit.commands

import OrbitInstance
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.InlineMessage
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import dev.minn.jda.ktx.messages.MessageCreateBuilder
import me.thestars.orbit.utils.locales.OrbitLocale
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.DiscordLocale

class UnleashedCommandContext(val event: SlashCommandInteractionEvent, client: OrbitInstance) {
    val jda = event.jda
    val instance = client

    private val parsedLocale = hashMapOf(
        DiscordLocale.PORTUGUESE_BRAZILIAN to "pt-br",
        DiscordLocale.ENGLISH_US to "en-us"
    )

    val locale = if (event.isFromGuild) {
        OrbitLocale(parsedLocale[event.guildLocale] ?: parsedLocale[event.userLocale] ?: "pt-br")
    } else {
        OrbitLocale(parsedLocale[event.userLocale] ?: "pt-br")
    }

    suspend fun reply(ephemeral: Boolean = false, block: InlineMessage<*>.() -> Unit): Message {
        val msg = MessageCreateBuilder {
            apply(block)
        }

        if (event.isAcknowledged) {
            return event.hook.sendMessage(msg.build()).await()
        } else {
            var deffer = event.deferReply(ephemeral).await()

            return deffer.sendMessage(msg.build()).await()
        }
    }

    suspend fun defer(ephemeral: Boolean = false) {
        event.deferReply(ephemeral).await()
    }

    fun makeReply(emoteId: String, content: String): String {
        return "$emoteId **|** $content"
    }
}