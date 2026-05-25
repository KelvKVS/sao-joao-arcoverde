package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    val id: String = "",
    val titulo: String,
    val subtitulo: String,
    val imagem: String
)