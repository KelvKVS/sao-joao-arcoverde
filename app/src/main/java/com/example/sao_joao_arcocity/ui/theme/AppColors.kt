package com.example.sao_joao_arcocity.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val fundo: Color,
    val card: Color,
    val cardAlt: Color,
    val barra: Color,
    val textoPrimario: Color,
    val textoSecundario: Color,
    val textoTerciario: Color,
    val divisor: Color,
    val bordaBusca: Color,
    val chip: Color,
    val imagemFundoAlpha: Float,
    val isDark: Boolean
)

val TemaEscuro = AppColors(
    fundo            = Color(0xFF05080C),
    card             = Color(0xFF101826),
    cardAlt          = Color(0xFF07101D),
    barra            = Color(0xCC0B0F14),
    textoPrimario    = Color.White,
    textoSecundario  = Color.LightGray,
    textoTerciario   = Color.Gray,
    divisor          = Color.White.copy(alpha = 0.15f),
    bordaBusca       = Color.White.copy(alpha = 0.40f),
    chip             = Color(0xFF1A1F25),
    imagemFundoAlpha = 1f,
    isDark           = true
)

val TemaClaro = AppColors(
    fundo            = Color(0xFFF0F2F5),
    card             = Color(0xFFFFFFFF),
    cardAlt          = Color(0xFFF5F7FA),
    barra            = Color(0xF0FFFFFF),
    textoPrimario    = Color(0xFF1A1A2E),
    textoSecundario  = Color(0xFF555570),
    textoTerciario   = Color(0xFF9999AA),
    divisor          = Color(0xFF1A1A2E).copy(alpha = 0.10f),
    bordaBusca       = Color(0xFF1A1A2E).copy(alpha = 0.25f),
    chip             = Color(0xFFE8E8F0),
    imagemFundoAlpha = 0.06f,
    isDark           = false
)

val LocalAppColors = compositionLocalOf { TemaEscuro }
