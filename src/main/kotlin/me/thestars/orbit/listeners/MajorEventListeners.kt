package me.thestars.orbit.listeners

import OrbitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import okhttp3.Dispatcher

class MajorEventListeners(private val instance: OrbitInstance): ListenerAdapter() {
private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onGenericInteractionCreate(event: GenericInteractionCreateEvent) {
        coroutineScope.launch {
            when (event) {
                is SlashCommandInteractionEvent -> {
                    val commandName = event.fullCommandName.split(" ").first()
                    val command = instance.commandHandler[commandName]?.create()

                    if (command != null) {
                        val context = Unleashed
                    }
                }
            }
        }
    }
}