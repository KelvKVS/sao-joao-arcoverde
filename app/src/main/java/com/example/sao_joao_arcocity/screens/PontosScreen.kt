package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.models.PontoResponse
import com.example.sao_joao_arcocity.network.RetrofitInstance
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

data class PontoCidade(
    val nome: String,
    val categoria: String,
    val descricao: String,
    val endereco: String,
    val horario: String,
    val icone: Int,
    val cor: Color,
    val fotos: List<String>,
    val latitude: Double,
    val longitude: Double
)

@Composable
fun PontosScreen(
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onAbrirDetalhe: (PontoCidade) -> Unit
) {
    var pontos by remember { mutableStateOf<List<PontoCidade>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf<String?>(null) }

    var pesquisa by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        try {
            val resposta = RetrofitInstance.api.buscarPontos()

            pontos = resposta.map { ponto ->
                ponto.toPontoCidade()
            }

            carregando = false
        } catch (e: Exception) {
            erro = e.message
            carregando = false
        }
    }

    val pontosFiltrados = pontos.filter { ponto ->
        val combinaPesquisa =
            pesquisa.isBlank() ||
                    ponto.nome.contains(pesquisa, ignoreCase = true) ||
                    ponto.categoria.contains(pesquisa, ignoreCase = true) ||
                    ponto.endereco.contains(pesquisa, ignoreCase = true)

        val combinaCategoria =
            categoriaSelecionada == "Todos" ||
                    ponto.categoria.equals(categoriaSelecionada, ignoreCase = true) ||
                    if (categoriaSelecionada == "Serviços")
                        ponto.categoria != "Alimentação"
                    else
                        false

        combinaPesquisa && combinaCategoria
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05080C))
    ) {
        Image(
            painter = painterResource(id = R.drawable.fundohome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 95.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Pontos da cidade",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Descubra lugares e serviços locais",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.4f),
                        RoundedCornerShape(18.dp)
                    )
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = pesquisa,
                        onValueChange = { pesquisa = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (pesquisa.isBlank()) {
                                Text(
                                    text = "Buscar por nome ou categoria",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CategoriaChip(
                    titulo = "Todos",
                    ativo = categoriaSelecionada == "Todos",
                    onClick = { categoriaSelecionada = "Todos" }
                )

                CategoriaChip(
                    titulo = "Alimentação",
                    ativo = categoriaSelecionada == "Alimentação",
                    onClick = { categoriaSelecionada = "Alimentação" }
                )

                CategoriaChip(
                    titulo = "Serviços",
                    ativo = categoriaSelecionada == "Serviços",
                    onClick = { categoriaSelecionada = "Serviços" }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (carregando) {
                Text(
                    text = "Carregando pontos...",
                    color = Color.White,
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

            if (!carregando && erro == null && pontosFiltrados.isEmpty()) {
                Text(
                    text = "Nenhum ponto encontrado.",
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            pontosFiltrados.forEach { ponto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF101826))
                        .clickable {
                            onAbrirDetalhe(ponto)
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(ponto.cor),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = ponto.icone),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = ponto.nome,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = ponto.categoria,
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }

                    Text(
                        text = ">",
                        color = Color.LightGray,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            telaAtual = "pontos",
            onHomeClick = onIrHome,
            onProgramacaoClick = onIrProgramacao,
            onLiveClick = onIrLive,
            onpontosClick = {},
            onSobreClick = {}
        )
    }
}

fun PontoResponse.toPontoCidade(): PontoCidade {
    return PontoCidade(
        nome = nome,
        categoria = categoria,
        descricao = descricao,
        endereco = endereco,
        horario = horario,
        icone = escolherIcone(categoria),
        cor = escolherCor(categoria),
        fotos = fotos,
        latitude = latitude,
        longitude = longitude
    )
}

fun escolherIcone(categoria: String): Int {
    return when (categoria.lowercase()) {
        "alimentação" -> R.drawable.food
        "saúde" -> R.drawable.plus
        else -> R.drawable.location
    }
}

fun escolherCor(categoria: String): Color {
    return when (categoria.lowercase()) {
        "alimentação" -> Color(0xFFFFC107)
        "saúde" -> Color(0xFF6ED7C8)
        else -> Color(0xFF4D8DFF)
    }
}

@Composable
fun CategoriaChip(
    titulo: String,
    ativo: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (ativo)
                    Color(0xFFFFC107)
                else
                    Color(0xFF1A1F25)
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = titulo,
            color =
                if (ativo)
                    Color.Black
                else
                    Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PontosPreview() {
    Sao_joao_arcocityTheme {
        PontosScreen(
            onIrHome = {},
            onIrProgramacao = {},
            onIrLive = {},
            onAbrirDetalhe = {}
        )
    }
}