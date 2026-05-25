package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class BannerRequest(
    val titulo: String,
    val subtitulo: String,
    val imagem: String
)
