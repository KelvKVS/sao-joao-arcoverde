package com.example.sao_joao_arcocity.models

data class AlertaResponse(
    val id: String = "",
    val titulo: String,
    val mensagem: String,
    val tipo: String,
    val ativo: Boolean = true,
    val data: String
)
