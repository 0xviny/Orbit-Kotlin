package me.thestars.orbit.commands.vanilla.utils.declarations

import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationBuilder
import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationWrapper
import me.thestars.orbit.commands.vanilla.utils.PingExecutor

class OrbitCommand : OrbitSlashCommandDeclarationWrapper {
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
    }
}