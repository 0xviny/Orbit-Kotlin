package me.thestars.orbit.commands

import OrbitInstance
import dev.minn.jda.ktx.coroutines.await
import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationWrapper
import me.thestars.orbit.commands.vanilla.utils.declarations.OrbitCommand
import net.dv8tion.jda.api.interactions.commands.Command

class OrbitCommandManager(val instance: OrbitInstance) {
    val commands = mutableListOf<OrbitSlashCommandDeclarationWrapper>()

    operator fun get(name: String): OrbitSlashCommandDeclarationWrapper? {
        return commands.find { it.create().name == name }
    }

    private fun register(command: OrbitSlashCommandDeclarationWrapper) {
        commands.add(command)
    }

    suspend fun handle(): MutableList<Command>? {
        val action = instance.jda.updateCommands()
        val privateGuild = instance.jda.getGuildById(instance.config.get("guild_id"))!!

        commands.forEach { command ->
            if (command.create().isPrivate) {
                privateGuild.updateCommands().addCommands(
                    command.create().build()
                ).await()
            } else {
                action.addCommands(
                    command.create().build()
                )
            }
        }

        return action.await()
    }

    init {
        register(OrbitCommand())
    }
}