package me.thestars.orbit.commands.vanilla.utils.declarations

import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationBuilder
import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationWrapper
import me.thestars.orbit.commands.vanilla.utils.HelpExecutor
import me.thestars.orbit.commands.vanilla.utils.PingExecutor

class OrbitCommand : OrbitSlashCommandDeclarationWrapper {
    companion object {
        val LOCALE_PREFIX = "commands.command"
    }

    override fun create() = command(
        "orbit",
        "orbit.description"
    ) {
        subCommand(
            "ping",
            "orbit.ping.description",
            baseName = this@command.name
        ) {
            executor = PingExecutor()
        }

        subCommand(
            "help",
            "orbit.help.description",
            baseName = this@command.name
        ) {
            executor = HelpExecutor()
        }
    }
}