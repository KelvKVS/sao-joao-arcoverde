package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.PontoResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class PontoRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("pontos")

    suspend fun listarTodos(): List<PontoResponse> {
        return collection.find().toList().map { doc -> doc.toPontoResponse() }
    }

    suspend fun buscarPorId(id: String): PontoResponse? {
        return try {
            val objectId = ObjectId(id)
            collection.find(Filters.eq("_id", objectId)).toList()
                .firstOrNull()?.toPontoResponse()
        } catch (e: Exception) {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Document.toPontoResponse() = PontoResponse(
        id        = getObjectId("_id").toHexString(),
        nome      = getString("nome") ?: "",
        categoria = getString("categoria") ?: "",
        descricao = getString("descricao") ?: "",
        endereco  = getString("endereco") ?: "",
        horario   = getString("horario") ?: "",
        latitude  = getDouble("latitude") ?: 0.0,
        longitude = getDouble("longitude") ?: 0.0,
        fotos     = get("fotos") as? List<String> ?: emptyList()
    )
}
