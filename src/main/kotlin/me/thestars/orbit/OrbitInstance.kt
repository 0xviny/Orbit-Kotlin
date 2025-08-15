import me.thestars.orbit.commands.OrbitCommandManager
import me.thestars.orbit.listeners.MajorEventListeners
import me.thestars.orbit.utils.OrbitConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

class OrbitInstance {
    lateinit var jda: JDA
    lateinit var config: OrbitConfig
    lateinit var commandHandler: OrbitCommandManager

    fun start() {
        config = OrbitConfig()
        commandHandler = OrbitCommandManager(this)

        jda = JDABuilder.createDefault(config.get("discord_token")).build()
        jda.addEventListener(MajorEventListeners(this))
        jda.awaitReady()
    }
}