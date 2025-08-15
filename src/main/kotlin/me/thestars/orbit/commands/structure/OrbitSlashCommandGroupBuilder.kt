package me.thestars.orbit.commands.structure

class OrbitSlashCommandGroupBuilder(val name: String, val description: String) {
    val subCommands = mutableListOf<OrbitSlashCommandDeclarationBuilder>()

    fun subCommand(
        name: String,
        description: String,
        isPrivate: Boolean = false,
        block: OrbitSlashCommandDeclarationBuilder.() -> Unit
    ) {
        val subCommand = OrbitSlashCommandDeclarationBuilder(name, description, isPrivate)
        subCommand.block()
        subCommands.add(subCommand)
    }

    fun getSubCommand(name: String): OrbitSlashCommandDeclarationBuilder? {
        return subCommands.find { it.name == name }
    }
}