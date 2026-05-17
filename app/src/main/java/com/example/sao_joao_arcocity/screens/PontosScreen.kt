package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

data class PontoCidade(
    val nome: String,
    val categoria: String,
    val descricao: String,
    val endereco: String,
    val horario: String,
    val icone: Int,
    val cor: Color,
    val fotos: List<Int>
)

@Composable
fun PontosScreen(
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onAbrirDetalhe: (PontoCidade) -> Unit
) {
    val pontos = listOf(
        PontoCidade(
            nome = "Só delícias",
            categoria = "Alimentação",
            descricao = "Lanches e diversidade!",
            endereco = "Av. Severiano José Freire, 411 - Centro",
            horario = "22:00",
            icone = R.drawable.food,
            cor = Color(0xFFFFC107),
            fotos = listOf(
                R.drawable.comidas,
                R.drawable.comidas2,
                R.drawable.comidas3
            )
        ),
        PontoCidade(
            nome = "Farma Arcoverde",
            categoria = "Saúde",
            descricao = "Farmácia e produtos de saúde.",
            endereco = "Centro - Arcoverde",
            horario = "21:00",
            icone = R.drawable.plus,
            cor = Color(0xFF6ED7C8),
            fotos = listOf(
                R.drawable.plus,
                R.drawable.banner,
                R.drawable.show
            )
        )
    )

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

                    Text(
                        text = "Buscar por nome ou categoria",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CategoriaChip(titulo = "Todos", ativo = true)
                CategoriaChip(titulo = "Alimentação")
                CategoriaChip(titulo = "Serviços")
            }

            Spacer(modifier = Modifier.height(28.dp))

            pontos.forEach { ponto ->
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
            onpontosClick = {}
        )
    }
}

@Composable
fun CategoriaChip(
    titulo: String,
    ativo: Boolean = false
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (ativo) Color(0xFFFFC107) else Color(0xFF1A1F25)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = titulo,
            color = if (ativo) Color.Black else Color.White,
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