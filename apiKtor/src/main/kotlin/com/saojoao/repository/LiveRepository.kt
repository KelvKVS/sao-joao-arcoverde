package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.LiveResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document

class LiveRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("lives")

    // Retorna a live com ativa = true; se não houver, retorna a mais recente
    suspend fun buscarLiveAtiva(): LiveResponse? {
        val ativa = collection.find(Filters.eq("ativa", true)).toList().firstOrNull()
        val doc   = ativa ?: collection.find().toList().lastOrNull()

        return doc?.let {
            LiveResponse(
                id         = it.getObjectId("_id").toHexString(),
                titulo     = it.getString("titulo") ?: "",
                youtubeUrl = it.getString("youtubeUrl") ?: "",
                ativa      = it.getBoolean("ativa") ?: false
            )
        }
    }
}
