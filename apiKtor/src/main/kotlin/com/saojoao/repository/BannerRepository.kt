package com.saojoao.repository

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.saojoao.models.BannerResponse
import kotlinx.coroutines.flow.toList
import org.bson.Document

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
}
