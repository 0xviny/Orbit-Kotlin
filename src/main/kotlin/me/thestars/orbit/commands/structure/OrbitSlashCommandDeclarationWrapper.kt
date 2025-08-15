package me.thestars.orbit.commands.structure

interface OrbitSlashCommandDeclarationWrapper {
    fun create(): OrbitSlashCommandDeclarationBuilder

    fun command(
        name: String,
        description: String,
        isPrivate: Boolean = false,
        block: OrbitSlashCommandDeclarationBuilder.() -> Unit
    ): OrbitSlashCommandDeclarationBuilder {
        return OrbitSlashCommandDeclarationBuilder(name, description, isPrivate).apply(block)
    }
}