package com.saojoao.database

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*

lateinit var mongoDatabase: MongoDatabase
    private set

fun Application.configureDatabase() {
    val mongoUri      = environment.config.property("mongo.uri").getString()
    val databaseName  = environment.config.property("mongo.database").getString()

    val client = MongoClient.create(mongoUri)
    mongoDatabase = client.getDatabase(databaseName)

    log.info("✅ MongoDB conectado: $databaseName")
}
