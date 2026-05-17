package com.saojoao.data

import com.saojoao.models.ProgramacaoResponse

val programacoesMock = listOf(
    ProgramacaoResponse(
        id = 1,
        horario = "18:00",
        titulo = "Abertura Oficial",
        local = "Praça Central",
        dia = "21 JUN",
        semana = "Sexta",
        categoria = "Cerimônia",
        imagem = "show.png"
    ),
    ProgramacaoResponse(
        id = 2,
        horario = "19:00",
        titulo = "Trio Pé de Serra",
        local = "Palco Principal",
        dia = "21 JUN",
        semana = "Sexta",
        categoria = "Forró",
        imagem = "trio.png"
    ),
    ProgramacaoResponse(
        id = 3,
        horario = "20:00",
        titulo = "Forró dos Bons",
        local = "Palco Principal",
        dia = "21 JUN",
        semana = "Sexta",
        categoria = "Show",
        imagem = "forro.png"
    ),
    ProgramacaoResponse(
        id = 4,
        horario = "21:00",
        titulo = "João Gomes",
        local = "Palco Principal",
        dia = "21 JUN",
        semana = "Sexta",
        categoria = "Show",
        imagem = "joaogomes.png"
    )
)