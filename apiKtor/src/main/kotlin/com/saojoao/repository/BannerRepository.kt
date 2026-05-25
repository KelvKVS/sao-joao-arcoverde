package com.saojoao.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.BannerRequest
import com.saojoao.models.BannerResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class BannerRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Document>("banners")

    suspend fun listarTodos(): List<BannerResponse> {
        return collection.find().toList().map { doc ->
            BannerResponse(
                id        = doc.getObjectId("_id").toHexString(),
                titulo    = doc.getString("titulo") ?: "",
                subtitulo = doc.getString("subtitulo") ?: "",
                imagem    = doc.getString("imagem") ?: ""
            )
        }
    }

    suspend fun inserir(request: BannerRequest): BannerResponse {
        val newId = ObjectId()
        val doc = Document("_id", newId)
            .append("titulo", request.titulo)
            .append("subtitulo", request.subtitulo)
            .append("imagem", request.imagem)
        collection.insertOne(doc)
        return BannerResponse(
            id        = newId.toHexString(),
            titulo    = request.titulo,
            subtitulo = request.subtitulo,
            imagem    = request.imagem
        )
    }

    suspend fun atualizar(id: String, request: BannerRequest): Boolean {
        return try {
            val objectId = ObjectId(id)
            val doc = Document("_id", objectId)
                .append("titulo", request.titulo)
                .append("subtitulo", request.subtitulo)
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
}
