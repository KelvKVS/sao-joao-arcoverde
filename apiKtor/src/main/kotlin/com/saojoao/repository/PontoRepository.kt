package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.PontoRequest
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

    suspend fun inserir(request: PontoRequest): PontoResponse {
        val newId = ObjectId()
        val doc = Document("_id", newId)
            .append("nome", request.nome)
            .append("categoria", request.categoria)
            .append("descricao", request.descricao)
            .append("endereco", request.endereco)
            .append("horario", request.horario)
            .append("latitude", request.latitude)
            .append("longitude", request.longitude)
            .append("fotos", request.fotos)
        collection.insertOne(doc)
        return PontoResponse(
            id        = newId.toHexString(),
            nome      = request.nome,
            categoria = request.categoria,
            descricao = request.descricao,
            endereco  = request.endereco,
            horario   = request.horario,
            latitude  = request.latitude,
            longitude = request.longitude,
            fotos     = request.fotos
        )
    }

    suspend fun atualizar(id: String, request: PontoRequest): Boolean {
        return try {
            val objectId = ObjectId(id)
            val doc = Document("_id", objectId)
                .append("nome", request.nome)
                .append("categoria", request.categoria)
                .append("descricao", request.descricao)
                .append("endereco", request.endereco)
                .append("horario", request.horario)
                .append("latitude", request.latitude)
                .append("longitude", request.longitude)
                .append("fotos", request.fotos)
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
