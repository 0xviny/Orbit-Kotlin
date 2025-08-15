package me.thestars.orbit.utils.locales

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream

class OrbitLocale(val locale: String) {
    private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    companion object {
        const val PATH = "locales"
    }

    operator fun get(key: String, vararg placeholder: String): String {
        val resourcePath = "$PATH/$locale/general.yml"
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream(resourcePath)

        if (inputStream == null) {
            return "!!{${key}}!! Arquivo não econtrado: $resourcePath"
        }

        val tree = mapper.readTree(inputStream)

        val keytList = key.split(".")
        var current = tree

        for (k in keytList) {
            current = current.get(k)
            if (current == null) {
                println("Chave não encontrada: $key em $resourcePath")
                return "!!{${key}}!!"
            }
        }

        var result = current.asText()

        placeholder.forEachIndexed { index, s ->
            result = result.replace("{${index}}", s)
        }

        return result
    }
}