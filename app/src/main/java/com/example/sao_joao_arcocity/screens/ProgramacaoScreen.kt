package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.content.Context
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.helpers.reagendarFavoritos
import com.example.sao_joao_arcocity.models.ProgramacaoResponse
import com.example.sao_joao_arcocity.network.RetrofitInstance
import com.example.sao_joao_arcocity.ui.theme.LocalAppColors
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

data class EventoProgramacao(
    val id: String,
    val horario: String,
    val titulo: String,
    val local: String,
    val dia: String,
    val semana: String,
    val categoria: String,
    val imagem: Int
)

@Composable
fun ProgramacaoScreen(
    onIrHome: () -> Unit,
    onIrLive: () -> Unit,
    onIrPontos: () -> Unit,
    onIrSobre: () -> Unit
) {
    val colors = LocalAppColors.current
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("SaoJoaoPrefs", Context.MODE_PRIVATE) }

    var pesquisa by remember { mutableStateOf("") }
    var diaSelecionado by remember { mutableStateOf("21 JUN") }
    var favoritos by remember {
        mutableStateOf(prefs.getStringSet("favoritos", emptySet())?.toSet() ?: emptySet())
    }
    var eventos by remember { mutableStateOf<List<EventoProgramacao>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val resposta = RetrofitInstance.api.buscarProgramacoes()
            eventos = resposta.map { it.toEventoProgramacao() }
            carregando = false
        } catch (e: Exception) {
            erro = e.message
            carregando = false
        }
    }

    val dias = listOf(
        DiaEvento("21 JUN", "Sexta", true),
        DiaEvento("22 JUN", "Sábado"),
        DiaEvento("23 JUN", "Domingo"),
        DiaEvento("24 JUN", "Segunda")
    )

    val eventosFiltrados = eventos.filter {
        it.dia == diaSelecionado &&
        (pesquisa.isBlank() ||
                it.titulo.contains(pesquisa, ignoreCase = true) ||
                it.categoria.contains(pesquisa, ignoreCase = true) ||
                it.local.contains(pesquisa, ignoreCase = true))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.fundo)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fundohome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(colors.imagemFundoAlpha),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 90.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Programação",
                        color = colors.textoPrimario,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Confira a agenda dos dias de festa!",
                        color = colors.textoSecundario,
                        fontSize = 14.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    colorFilter = ColorFilter.tint(colors.textoPrimario)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, colors.bordaBusca, RoundedCornerShape(18.dp))
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(colors.textoTerciario)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = pesquisa,
                        onValueChange = { pesquisa = it },
                        textStyle = TextStyle(
                            color = colors.textoPrimario,
                            fontSize = 15.sp
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (pesquisa.isEmpty()) {
                                Text(
                                    text = "Buscar por nome ou categoria",
                                    color = colors.textoTerciario,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(dias) { dia ->
                    val ativo = dia.dia == diaSelecionado
                    Box(
                        modifier = Modifier
                            .width(78.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { diaSelecionado = dia.dia }
                            .background(if (ativo) Color(0xFFFFC107) else Color.Transparent)
                            .border(
                                width = if (ativo) 0.dp else 1.dp,
                                color = colors.textoPrimario,
                                shape = RoundedCornerShape(18.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dia.dia,
                                color = if (ativo) Color.Black else colors.textoPrimario,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = dia.semana,
                                color = if (ativo) Color.Black else colors.textoPrimario,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (carregando) {
                Text(
                    text = "Carregando programação...",
                    color = colors.textoPrimario,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            erro?.let {
                Text(
                    text = "Erro ao carregar: $it",
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            eventosFiltrados.forEach { evento ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = evento.horario,
                            color = Color(0xFFFFC107),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = evento.semana,
                            color = colors.textoSecundario,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = evento.imagem),
                        contentDescription = null,
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = evento.titulo,
                            color = colors.textoPrimario,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = evento.local,
                            color = colors.textoSecundario,
                            fontSize = 13.sp
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Favoritar",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                val novosFavoritos = if (evento.id in favoritos)
                                    favoritos - evento.id
                                else
                                    favoritos + evento.id
                                favoritos = novosFavoritos
                                prefs.edit().putStringSet("favoritos", novosFavoritos).apply()
                                reagendarFavoritos(context, novosFavoritos, eventos)
                            },
                        colorFilter = ColorFilter.tint(
                            if (evento.id in favoritos) Color(0xFFFFC107) else colors.textoPrimario
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 20.dp)
                        .background(colors.divisor)
                )

                Spacer(modifier = Modifier.height(18.dp))
            }
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            telaAtual = "programacao",
            onHomeClick = onIrHome,
            onProgramacaoClick = {},
            onLiveClick = onIrLive,
            onpontosClick = onIrPontos,
            onSobreClick = onIrSobre
        )
    }
}

fun ProgramacaoResponse.toEventoProgramacao(): EventoProgramacao {
    return EventoProgramacao(
        id        = id,
        horario   = horario,
        titulo    = titulo,
        local     = local,
        dia       = dia,
        semana    = semana,
        categoria = categoria,
        imagem    = escolherImagemProgramacao(imagem)
    )
}

fun escolherImagemProgramacao(imagem: String): Int {
    return when (imagem.lowercase()) {
        "show.png" -> R.drawable.show
        "trio.png" -> R.drawable.trio
        "forro.png" -> R.drawable.forro
        "joaogomes.png" -> R.drawable.joaogomes
        "encerramento.png" -> R.drawable.encerramento
        else -> R.drawable.show
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProgramacaoPreview() {
    Sao_joao_arcocityTheme {
        ProgramacaoScreen(
            onIrHome = {},
            onIrLive = {},
            onIrPontos = {},
            onIrSobre = {}
        )
    }
}
