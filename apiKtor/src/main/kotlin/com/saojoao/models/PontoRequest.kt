package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class PontoRequest(
    val nome: String,
    val categoria: String,
    val tipo: String = "servico",
    val descricao: String,
    val endereco: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double,
    val fotos: List<String>
)
