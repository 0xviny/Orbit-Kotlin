package me.thestars.orbit.commands.vanilla.utils

import me.thestars.orbit.commands.UnleashedCommandContext
import me.thestars.orbit.commands.structure.OrbitSlashCommandExecutor
import me.thestars.orbit.commands.vanilla.utils.declarations.OrbitCommand.Companion.LOCALE_PREFIX
import me.thestars.orbit.utils.Constants
import net.dv8tion.jda.api.interactions.components.buttons.Button

class HelpExecutor : OrbitSlashCommandExecutor() {
    override suspend fun execute(context: UnleashedCommandContext) {
        context.reply {
            embed {
                title = context.locale["$LOCALE_PREFIX.help.embedTitle"]
                description = context.locale["$LOCALE_PREFIX.help.embedDescription", context.event.user.asMention]
                color = Constants.DEFAULT_COLOR

                field {
                    name = context.locale["$LOCALE_PREFIX.help.fieldCommands"]
                    value =
                        "[[${context.locale["$LOCALE_PREFIX.help.fieldLinkCommand"]}]](https://orbitbot.website/commands)"
                    inline = true
                }
                field {
                    name = context.locale["$LOCALE_PREFIX.help.fieldSupport"]
                    value =
                        "[[${context.locale["$LOCALE_PREFIX.help.fieldLinkSupport"]}]](https://orbitbot.website/discord)"
                    inline = true
                }
                field {
                    name = context.locale["$LOCALE_PREFIX.help.fieldDashboard"]
                    value = "[[Dashboard]](https://orbitbot.website/)"
                    inline = true
                }

                thumbnail = context.jda.selfUser.avatarUrl
            }

            actionRow(
                Button.link("https://orbitbot.website/invite", context.locale["$LOCALE_PREFIX.link.inviteMe"]),
                Button.link("https://orbitbot.website/", context.locale["$LOCALE_PREFIX.link.dashboard"]),
                Button.link("https://orbitbot.website/discord", context.locale["$LOCALE_PREFIX.link.support"])
            )
        }
    }
}