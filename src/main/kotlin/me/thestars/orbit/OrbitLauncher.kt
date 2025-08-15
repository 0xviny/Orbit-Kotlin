package me.thestars.orbit

import OrbitInstance
import kotlinx.coroutines.runBlocking

object OrbitLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            OrbitInstance().start()
        }
    }
}