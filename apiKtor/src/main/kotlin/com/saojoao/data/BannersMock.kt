package com.saojoao.data

import com.saojoao.models.BannerResponse

val bannersMock = listOf(
    BannerResponse(
        id = 1,
        titulo = "São João Arco City",
        subtitulo = "O maior São João da região!",
        imagem = "banner.png"
    ),
    BannerResponse(
        id = 2,
        titulo = "Shows ao vivo",
        subtitulo = "Acompanhe as atrações principais",
        imagem = "show.png"
    ),
    BannerResponse(
        id = 3,
        titulo = "Forró e tradição",
        subtitulo = "Muito arrasta-pé pra você curtir",
        imagem = "forro.png"
    )
)