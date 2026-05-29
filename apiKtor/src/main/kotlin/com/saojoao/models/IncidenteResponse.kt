package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class IncidenteResponse(
    val id: String = "",
    val tipo: String,
    val descricao: String,
    val endereco: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val userId: String,
    val status: String = "aberto",
    val data: String
)
