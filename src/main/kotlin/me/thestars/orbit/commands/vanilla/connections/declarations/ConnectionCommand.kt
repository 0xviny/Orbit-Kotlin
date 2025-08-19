package me.thestars.orbit.commands.vanilla.connections.declarations

import me.thestars.orbit.commands.structure.OrbitSlashCommandDeclarationWrapper
import me.thestars.orbit.commands.vanilla.connections.ConnectionJoinExecutor
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class ConnectionCommand : OrbitSlashCommandDeclarationWrapper {
    companion object {
        val LOCALE_PREFIX = "commands.command"
    }

    override fun create() = command(
        "connection",
        "connection.description"
    ) {
        subCommand(
            "join",
            "connection.join.description",
            baseName = this@command.name
        ) {
            addOption(
                OptionData(OptionType.STRING, "connection", "connection.options.name.description").setRequired(true).setAutoComplete(true),
                isSubCommand = true,
                baseName = this.baseName
            )

            addOption(
                OptionData(OptionType.CHANNEL, "connection_channel", "connection.options.channel.description").setRequired(true).setChannelTypes(
                    ChannelType.TEXT),
                isSubCommand = true,
                baseName = this.baseName
            )

            executor = ConnectionJoinExecutor()
        }
    }
}