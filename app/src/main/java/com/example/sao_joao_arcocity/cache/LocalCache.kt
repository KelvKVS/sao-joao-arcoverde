package com.example.sao_joao_arcocity.cache

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object LocalCache {
    @PublishedApi internal val gson = Gson()

    fun salvar(context: Context, nome: String, dados: Any) {
        try {
            context.filesDir.resolve(nome).writeText(gson.toJson(dados))
        } catch (_: Exception) {}
    }

    inline fun <reified T> carregar(context: Context, nome: String): T? {
        return try {
            val texto = context.filesDir.resolve(nome).readText()
            gson.fromJson(texto, object : TypeToken<T>() {}.type)
        } catch (_: Exception) {
            null
        }
    }
}
