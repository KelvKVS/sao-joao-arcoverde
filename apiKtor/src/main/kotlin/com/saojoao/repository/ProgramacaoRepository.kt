package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.ProgramacaoRequest
import com.saojoao.models.ProgramacaoResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class ProgramacaoRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("programacoes")

    suspend fun listarTodas(): List<ProgramacaoResponse> {
        return collection.find().toList().map { doc -> doc.toProgramacaoResponse() }
    }

    suspend fun buscarPorId(id: String): ProgramacaoResponse? {
        return try {
            val objectId = ObjectId(id)
            collection.find(Filters.eq("_id", objectId)).toList()
                .firstOrNull()?.toProgramacaoResponse()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun inserir(request: ProgramacaoRequest): ProgramacaoResponse {
        val newId = ObjectId()
        val doc = Document("_id", newId)
            .append("horario", request.horario)
            .append("titulo", request.titulo)
            .append("local", request.local)
            .append("dia", request.dia)
            .append("semana", request.semana)
            .append("categoria", request.categoria)
            .append("imagem", request.imagem)
        collection.insertOne(doc)
        return ProgramacaoResponse(
            id        = newId.toHexString(),
            horario   = request.horario,
            titulo    = request.titulo,
            local     = request.local,
            dia       = request.dia,
            semana    = request.semana,
            categoria = request.categoria,
            imagem    = request.imagem
        )
    }

    suspend fun atualizar(id: String, request: ProgramacaoRequest): Boolean {
        return try {
            val objectId = ObjectId(id)
            val doc = Document("_id", objectId)
                .append("horario", request.horario)
                .append("titulo", request.titulo)
                .append("local", request.local)
                .append("dia", request.dia)
                .append("semana", request.semana)
                .append("categoria", request.categoria)
                .append("imagem", request.imagem)
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

    private fun Document.toProgramacaoResponse() = ProgramacaoResponse(
        id        = getObjectId("_id").toHexString(),
        horario   = getString("horario") ?: "",
        titulo    = getString("titulo") ?: "",
        local     = getString("local") ?: "",
        dia       = getString("dia") ?: "",
        semana    = getString("semana") ?: "",
        categoria = getString("categoria") ?: "",
        imagem    = getString("imagem") ?: ""
    )
}
