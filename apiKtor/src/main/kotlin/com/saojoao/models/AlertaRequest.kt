package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class AlertaRequest(
    val titulo: String,
    val mensagem: String,
    val tipo: String,
    val ativo: Boolean = true
)
