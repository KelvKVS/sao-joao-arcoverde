package com.example.sao_joao_arcocity

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.sao_joao_arcocity.helpers.agendarResumoDiario
import com.example.sao_joao_arcocity.helpers.criarCanalNotificacao
import com.example.sao_joao_arcocity.screens.DetalhePontoScreen
import com.example.sao_joao_arcocity.screens.HistoriaScreen
import com.example.sao_joao_arcocity.screens.HomeScreen
import com.example.sao_joao_arcocity.screens.LiveScreen
import com.example.sao_joao_arcocity.screens.LoginScreen
import com.example.sao_joao_arcocity.screens.PontoCidade
import com.example.sao_joao_arcocity.screens.PontosScreen
import com.example.sao_joao_arcocity.screens.ProgramacaoScreen
import com.example.sao_joao_arcocity.screens.RotaMapaScreen
import com.example.sao_joao_arcocity.screens.SobreScreen
import com.example.sao_joao_arcocity.ui.theme.LocalAppColors
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme
import com.example.sao_joao_arcocity.ui.theme.TemaClaro
import com.example.sao_joao_arcocity.ui.theme.TemaEscuro

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        criarCanalNotificacao(this)
        agendarResumoDiario(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }

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
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("SaoJoaoPrefs", Context.MODE_PRIVATE)
    }

    var nomeUsuario by remember {
        mutableStateOf(sharedPreferences.getString("nomeUsuario", "") ?: "")
    }

    var tela by remember {
        mutableStateOf(if (nomeUsuario.isNotBlank()) "home" else "login")
    }

    var pontoSelecionado by remember {
        mutableStateOf<PontoCidade?>(null)
    }

    var isDark by remember { mutableStateOf(true) }
    val cores = if (isDark) TemaEscuro else TemaClaro

    CompositionLocalProvider(LocalAppColors provides cores) {
        when (tela) {

            "home" -> {
                HomeScreen(
                    nome = nomeUsuario,
                    onIrProgramacao = { tela = "programacao" },
                    onIrLive = { tela = "live" },
                    onIrPontos = { tela = "pontos" },
                    onIrSobre = { tela = "sobre" },
                    onIrHistoria = { tela = "historia" },
                    onToggleTema = { isDark = !isDark }
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
                        onFechar = { tela = "detalhePonto" }
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

            "historia" -> {
                HistoriaScreen(
                    onIrHome = { tela = "home" },
                    onIrProgramacao = { tela = "programacao" },
                    onIrLive = { tela = "live" },
                    onIrPontos = { tela = "pontos" },
                    onIrSobre = { tela = "sobre" }
                )
            }

            else -> {
                LoginScreen(
                    onEntrar = { nome ->
                        val nomeSalvo = if (nome.isBlank()) "usuario" else nome
                        nomeUsuario = nomeSalvo
                        sharedPreferences.edit().putString("nomeUsuario", nomeSalvo).apply()
                        tela = "home"
                    }
                )
            }
        }
    }
}
