package me.thestars.orbit.commands.structure

import me.thestars.orbit.commands.UnleashedCommandContext

abstract class OrbitSlashCommandExecutor {
    abstract suspend fun execute(context: UnleashedCommandContext)
}