package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class IncidenteRequest(
    val tipo: String,
    val descricao: String,
    val endereco: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val userId: String
)
