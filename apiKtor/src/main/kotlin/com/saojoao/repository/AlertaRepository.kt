package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.AlertaRequest
import com.saojoao.models.AlertaResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime

class AlertaRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("alertas")

    suspend fun listarAtivos(): List<AlertaResponse> =
        collection.find(Filters.eq("ativo", true)).toList().map { it.toAlertaResponse() }

    suspend fun inserir(request: AlertaRequest): AlertaResponse {
        val newId = ObjectId()
        val data = LocalDateTime.now().toString()
        val doc = Document("_id", newId)
            .append("titulo", request.titulo)
            .append("mensagem", request.mensagem)
            .append("tipo", request.tipo)
            .append("ativo", request.ativo)
            .append("data", data)
        collection.insertOne(doc)
        return AlertaResponse(
            id = newId.toHexString(),
            titulo = request.titulo,
            mensagem = request.mensagem,
            tipo = request.tipo,
            ativo = request.ativo,
            data = data
        )
    }

    suspend fun desativar(id: String): Boolean {
        return try {
            val objectId = ObjectId(id)
            val update = Document("\$set", Document("ativo", false))
            collection.updateOne(Filters.eq("_id", objectId), update).modifiedCount > 0
        } catch (e: Exception) { false }
    }

    suspend fun deletar(id: String): Boolean {
        return try {
            collection.deleteOne(Filters.eq("_id", ObjectId(id))).deletedCount > 0
        } catch (e: Exception) { false }
    }

    private fun Document.toAlertaResponse() = AlertaResponse(
        id = getObjectId("_id").toHexString(),
        titulo = getString("titulo") ?: "",
        mensagem = getString("mensagem") ?: "",
        tipo = getString("tipo") ?: "info",
        ativo = getBoolean("ativo") ?: true,
        data = getString("data") ?: ""
    )
}
