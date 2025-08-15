import me.thestars.orbit.utils.OrbitConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

class OrbitInstance {
    lateinit var jda: JDA
    lateinit var config: OrbitConfig

    fun start() {
        jda = JDABuilder.createDefault(config.get("discord_token")).build()
        jda.awaitReady()
    }
}