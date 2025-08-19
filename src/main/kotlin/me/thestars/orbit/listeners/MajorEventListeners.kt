package me.thestars.orbit.listeners

import OrbitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.database.dao.OrbitConnection
import me.thestars.orbit.database.table.OrbitConnections
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import okhttp3.Dispatcher
import org.jetbrains.exposed.sql.and

class MajorEventListeners(private val instance: OrbitInstance) : ListenerAdapter() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onGenericInteractionCreate(event: GenericInteractionCreateEvent) {
        coroutineScope.launch {
            when (event) {
                is SlashCommandInteractionEvent -> {
                    val commandName = event.fullCommandName.split(" ").first()
                    val command = instance.commandHandler[commandName]?.create()

                    if (command != null) {
                        val context = UnleashedCommandContext(event, instance)

                        val subCommandGroupName = event.subcommandGroup
                        val subCommandName = event.subcommandName

                        val subCommandGroup =
                            if (subCommandGroupName != null) command.getSubCommandGroup(subCommandGroupName) else null
                        val subCommand = if (subCommandName != null) {
                            if (subCommandGroup != null) {
                                subCommandGroup.getSubCommand(subCommandName)
                            } else {
                                command.getSubCommand(subCommandName)
                            }
                        } else null

                        if (subCommand != null) {
                            subCommand.executor?.execute(context)
                        } else if (subCommandGroupName == null && subCommandName == null) {
                            command.executor?.execute(context)
                        }
                    }
                }
            }
        }
    }

    override fun onCommandAutoCompleteInteraction(event: CommandAutoCompleteInteractionEvent) {
        if (event.name == "connection" && event.subcommandName == "join" && event.focusedOption.name == "connection") {
            val input = event.focusedOption.value

            val matches = OrbitConnection.find {
                OrbitConnections.type eq 3 and (OrbitConnections.name like "$input%")
            }.limit(25).map { it.name }

            val choices = matches.map { Command.Choice(it, it) }

            event.replyChoices(choices).queue()
        }
    }

    override fun onReady(event: ReadyEvent) {
        coroutineScope.launch {
            event.jda.presence.setPresence(
                OnlineStatus.ONLINE,
                Activity.customStatus("Cuidando dos servidores ai!")
            )

            val commands = instance.commandHandler.handle()
            println("Registered ${commands?.size} commands")
        }
    }
}