package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
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