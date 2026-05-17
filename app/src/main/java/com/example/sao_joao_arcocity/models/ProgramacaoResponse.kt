package com.example.sao_joao_arcocity.models

data class ProgramacaoResponse(
    val id: Int,
    val horario: String,
    val titulo: String,
    val local: String,
    val dia: String,
    val semana: String,
    val categoria: String,
    val imagem: String
)