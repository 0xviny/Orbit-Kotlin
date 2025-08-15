package me.thestars.orbit.commands

import OrbitInstance
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class UnleashedCommandContext(val event: SlashCommandInteractionEvent, client: OrbitInstance) {
    val jda = event.jda
    val instance = client

    suspend fun reply(ephemeral: Boolean = false, block: )
}