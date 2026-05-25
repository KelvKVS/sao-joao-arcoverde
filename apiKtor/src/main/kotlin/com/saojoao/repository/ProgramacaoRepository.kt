package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
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
