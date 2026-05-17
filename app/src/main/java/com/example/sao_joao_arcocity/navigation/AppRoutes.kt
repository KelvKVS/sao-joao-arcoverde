package com.example.sao_joao_arcocity.navigation

sealed class AppRoutes(val route: String) {

    object Login : AppRoutes("login")

    object Home : AppRoutes("home")

    object Programacao : AppRoutes("programacao")

    object Live : AppRoutes("live")

    object Pontos : AppRoutes("pontos")

    object DetalhePonto : AppRoutes("detalhePonto")

    object RotaMapa : AppRoutes("rotaMapa")

    object Sobre : AppRoutes("sobre")
}