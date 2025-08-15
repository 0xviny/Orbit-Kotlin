import me.thestars.orbit.commands.OrbitCommandManager
import me.thestars.orbit.database.DatabaseService
import me.thestars.orbit.database.PostgresConfig
import me.thestars.orbit.listeners.MajorEventListeners
import me.thestars.orbit.utils.OrbitConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

class OrbitInstance {
    lateinit var jda: JDA
    lateinit var config: OrbitConfig
    lateinit var commandHandler: OrbitCommandManager
    var database: DatabaseService = DatabaseService

    fun start() {
        config = OrbitConfig()
        commandHandler = OrbitCommandManager(this)

        val configDb = PostgresConfig(
            url = config.get("database_url"),
            username = config.get("database_username"),
            password = config.get("database_password")
        )

        database.connect(configDb)

        jda = JDABuilder.createDefault(config.get("discord_token")).build()
        jda.addEventListener(MajorEventListeners(this))
        jda.awaitReady()
    }
}