package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class LiveResponse(
    val id: Int,
    val titulo: String,
    val youtubeUrl: String,
    val ativa: Boolean
)