package com.example.sao_joao_arcocity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.sao_joao_arcocity.screens.DetalhePontoScreen
import com.example.sao_joao_arcocity.screens.HomeScreen
import com.example.sao_joao_arcocity.screens.LiveScreen
import com.example.sao_joao_arcocity.screens.LoginScreen
import com.example.sao_joao_arcocity.screens.PontoCidade
import com.example.sao_joao_arcocity.screens.PontosScreen
import com.example.sao_joao_arcocity.screens.ProgramacaoScreen
import com.example.sao_joao_arcocity.screens.RotaMapaScreen
import com.example.sao_joao_arcocity.screens.SobreScreen
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Sao_joao_arcocityTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {

    var tela by remember {
        mutableStateOf("login")
    }

    var nomeUsuario by remember {
        mutableStateOf("")
    }

    var pontoSelecionado by remember {
        mutableStateOf<PontoCidade?>(null)
    }

    when (tela) {

        "home" -> {
            HomeScreen(
                nome = nomeUsuario,
                onIrProgramacao = { tela = "programacao" },
                onIrLive = { tela = "live" },
                onIrPontos = { tela = "pontos" },
                onIrSobre = { tela = "sobre" }
            )
        }

        "programacao" -> {
            ProgramacaoScreen(
                onIrHome = { tela = "home" },
                onIrLive = { tela = "live" },
                onIrPontos = { tela = "pontos" },

            )
        }

        "live" -> {
            LiveScreen(
                onIrHome = { tela = "home" },
                onIrProgramacao = { tela = "programacao" },
                onIrPontos = { tela = "pontos" },

            )
        }

        "pontos" -> {
            PontosScreen(
                onIrHome = { tela = "home" },
                onIrProgramacao = { tela = "programacao" },
                onIrLive = { tela = "live" },

                onAbrirDetalhe = { ponto ->
                    pontoSelecionado = ponto
                    tela = "detalhePonto"
                }
            )
        }

        "detalhePonto" -> {
            pontoSelecionado?.let { ponto ->
                DetalhePontoScreen(
                    ponto = ponto,
                    onVoltar = { tela = "pontos" },
                    onIrHome = { tela = "home" },
                    onIrProgramacao = { tela = "programacao" },
                    onIrLive = { tela = "live" },

                    onVerMapa = { tela = "rotaMapa" }
                )
            }
        }

        "rotaMapa" -> {
            pontoSelecionado?.let { ponto ->
                RotaMapaScreen(
                    ponto = ponto,
                    onFechar = {
                        tela = "detalhePonto"
                    }
                )
            }
        }

        "sobre" -> {
            SobreScreen(
                onIrHome = { tela = "home" },
                onIrProgramacao = { tela = "programacao" },
                onIrLive = { tela = "live" },
                onIrPontos = { tela = "pontos" }
            )
        }

        else -> {
            LoginScreen(
                onEntrar = { nome ->
                    nomeUsuario =
                        if (nome.isBlank()) "usuario" else nome

                    tela = "home"
                }
            )
        }
    }
}