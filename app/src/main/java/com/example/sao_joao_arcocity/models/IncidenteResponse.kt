package com.example.sao_joao_arcocity.models

data class IncidenteResponse(
    val id: String = "",
    val tipo: String,
    val descricao: String,
    val endereco: String,
    val userId: String,
    val status: String = "aberto",
    val data: String
)
