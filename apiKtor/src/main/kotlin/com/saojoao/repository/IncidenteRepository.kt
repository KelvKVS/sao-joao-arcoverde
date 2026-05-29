package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.IncidenteRequest
import com.saojoao.models.IncidenteResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class IncidenteRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("incidentes")

    suspend fun listarTodos(): List<IncidenteResponse> =
        collection.find().toList().map { it.toIncidenteResponse() }

    suspend fun listarAbertos(): List<IncidenteResponse> =
        collection.find(Filters.eq("status", "aberto")).toList().map { it.toIncidenteResponse() }

    suspend fun inserir(request: IncidenteRequest): IncidenteResponse {
        val newId = ObjectId()
        val data = LocalDateTime.now().toString()
        val doc = Document("_id", newId)
            .append("tipo", request.tipo)
            .append("descricao", request.descricao)
            .append("endereco", request.endereco)
            .append("latitude", request.latitude)
            .append("longitude", request.longitude)
            .append("userId", request.userId)
            .append("status", "aberto")
            .append("data", data)
        collection.insertOne(doc)
        return IncidenteResponse(
            id = newId.toHexString(),
            tipo = request.tipo,
            descricao = request.descricao,
            endereco = request.endereco,
            latitude = request.latitude,
            longitude = request.longitude,
            userId = request.userId,
            status = "aberto",
            data = data
        )
    }

    suspend fun resolver(id: String): Boolean {
        return try {
            val objectId = ObjectId(id)
            val update = Document("\$set", Document("status", "resolvido"))
            collection.updateOne(Filters.eq("_id", objectId), update).modifiedCount > 0
        } catch (e: Exception) { false }
    }

    private fun Document.toIncidenteResponse() = IncidenteResponse(
        id = getObjectId("_id").toHexString(),
        tipo = getString("tipo") ?: "",
        descricao = getString("descricao") ?: "",
        endereco = getString("endereco") ?: "",
        latitude = getDouble("latitude") ?: 0.0,
        longitude = getDouble("longitude") ?: 0.0,
        userId = getString("userId") ?: "",
        status = getString("status") ?: "aberto",
        data = getString("data") ?: ""
    )
}
