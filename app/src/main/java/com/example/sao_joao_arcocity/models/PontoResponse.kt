package com.example.sao_joao_arcocity.models

data class PontoResponse(
    val id: Int,
    val nome: String,
    val categoria: String,
    val descricao: String,
    val endereco: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double,
    val fotos: List<String>
)