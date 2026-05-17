package com.saojoao.data

import com.saojoao.models.PontoResponse

val pontosMock = listOf(
    PontoResponse(
        id = 1,
        nome = "Só delícias",
        categoria = "Alimentação",
        descricao = "Lanches e diversidade!",
        endereco = "Av. Severiano José Freire, 411 - Centro",
        horario = "22:00",
        latitude = -8.4186,
        longitude = -37.0538,
        fotos = listOf("https://lh3.googleusercontent.com/gps-cs-s/APNQkAGPOvU6_q3FyCFGz9zdUFN0_8zG9sFnJ7WAItQRtq-ogNKXPpsrzOFecOGnE0aKHEEoRPxseSk5vfKVXvS0xZ_iRWEFljL1qrWvz6PwD7XnKuJ7dsvFAnjckPiIKhKF5CJ5pNt39w=s680-w680-h510-rw", "https://lh3.googleusercontent.com/p/AF1QipNu0dsq8hpkZlIbU1eguLoHUlYAfPrQaKnBlset=s680-w680-h510-rw")
    ),
    PontoResponse(
        id = 2,
        nome = "Farmácia Pague Menos",
        categoria = "Saúde",
        descricao = "Av. Cel. Antônio Japiassú, 811 - Centro, Arcoverde - PE, 56506-100",
        endereco = "Centro - Arcoverde",
        horario = "21:00",
        latitude = -8.4200,
        longitude = -37.0520,
        fotos = listOf("https://www.drogarias.net.br/imgempresas/farmacias-pague-menos-127351-ATra.jpg", "https://www.drogarias.net.br/imgempresas/farmacias-pague-menos-127351-ATra.jpg")
    )
)