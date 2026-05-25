package com.saojoao.models

import kotlinx.serialization.Serializable

@Serializable
data class LiveRequest(
    val titulo: String,
    val youtubeUrl: String,
    val ativa: Boolean
)
