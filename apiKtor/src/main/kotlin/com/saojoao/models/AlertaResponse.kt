package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class AlertaResponse(
    val id: String = "",
    val titulo: String,
    val mensagem: String,
    val tipo: String,
    val ativo: Boolean = true,
    val data: String
)
