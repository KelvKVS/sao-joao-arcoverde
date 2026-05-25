package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.LiveRequest
import com.saojoao.models.LiveResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

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

    suspend fun listarTodas(): List<LiveResponse> {
        return collection.find().toList().map { doc ->
            LiveResponse(
                id         = doc.getObjectId("_id").toHexString(),
                titulo     = doc.getString("titulo") ?: "",
                youtubeUrl = doc.getString("youtubeUrl") ?: "",
                ativa      = doc.getBoolean("ativa") ?: false
            )
        }
    }

    suspend fun inserir(request: LiveRequest): LiveResponse {
        val newId = ObjectId()
        val doc = Document("_id", newId)
            .append("titulo", request.titulo)
            .append("youtubeUrl", request.youtubeUrl)
            .append("ativa", request.ativa)
        collection.insertOne(doc)
        return LiveResponse(
            id         = newId.toHexString(),
            titulo     = request.titulo,
            youtubeUrl = request.youtubeUrl,
            ativa      = request.ativa
        )
    }

    suspend fun atualizar(id: String, request: LiveRequest): Boolean {
        return try {
            val objectId = ObjectId(id)
            val doc = Document("_id", objectId)
                .append("titulo", request.titulo)
                .append("youtubeUrl", request.youtubeUrl)
                .append("ativa", request.ativa)
            val result = collection.replaceOne(Filters.eq("_id", objectId), doc)
            result.modifiedCount > 0
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deletar(id: String): Boolean {
        return try {
            val result = collection.deleteOne(Filters.eq("_id", ObjectId(id)))
            result.deletedCount > 0
        } catch (e: Exception) {
            false
        }
    }
}
