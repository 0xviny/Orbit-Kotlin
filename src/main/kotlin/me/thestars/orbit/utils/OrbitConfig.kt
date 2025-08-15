package me.thestars.orbit.utils

import okio.IOException
import java.util.Properties

class OrbitConfig {
    private val props = Properties()

    init {
        loadConfig()
    }

    private fun loadConfig() {
        val resourceStream = javaClass.classLoader.getResourceAsStream("orbit.conf")
            ?: throw IllegalStateException("Could not find orbit.conf")

        try {
            resourceStream.use { props.load(it) }
        } catch (e: IOException) {
            throw IllegalStateException("Could not load orbit.conf", e)
        }
    }

    fun get(key: String): String {
        return props.getProperty(key) ?: throw IllegalStateException("Could not find key $key in orbit.conf")
    }
}