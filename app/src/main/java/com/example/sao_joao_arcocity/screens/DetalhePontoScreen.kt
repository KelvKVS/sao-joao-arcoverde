package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.ui.theme.LocalAppColors
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

@Composable
fun DetalhePontoScreen(
    ponto: PontoCidade,
    onVoltar: () -> Unit,
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onVerMapa: () -> Unit,
    onIrSobre: () -> Unit = {},
    onIrReportarIncidente: () -> Unit = {}
) {
    val colors = LocalAppColors.current

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
                .padding(horizontal = 20.dp)
                .padding(bottom = 95.dp)
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(colors.card)
                        .clickable { onVoltar() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "♡",
                    color = colors.textoPrimario,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .background(ponto.cor)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = ponto.icone),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = ponto.nome,
                color = colors.textoPrimario,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFC107))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = ponto.categoria,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = ponto.descricao,
                color = colors.textoPrimario,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "📍 ${ponto.endereco}",
                color = colors.textoSecundario,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "● Aberto agora até ${ponto.horario}",
                color = Color(0xFF8AE66E),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { onVerMapa() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.locationbutton),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Ver no mapa",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.divisor)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Fotos",
                color = colors.textoPrimario,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(ponto.fotos.size) { index ->
                    AsyncImage(
                        model = ponto.fotos[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(105.dp)
                            .height(76.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF4A1010))
                .clickable { onIrReportarIncidente() }
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reportar problema neste local",
                color = Color(0xFFEF5350),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            telaAtual = "pontos",
            onHomeClick = onIrHome,
            onProgramacaoClick = onIrProgramacao,
            onLiveClick = onIrLive,
            onpontosClick = onVoltar,
            onSobreClick = onIrSobre
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalhePontoPreview() {
    Sao_joao_arcocityTheme {
        DetalhePontoScreen(
            ponto = PontoCidade(
                nome = "Só delícias",
                categoria = "Alimentação",
                descricao = "Lanches e diversidade!",
                endereco = "Av. Severiano José Freire, 411 - Centro",
                horario = "22:00",
                icone = R.drawable.food,
                cor = Color(0xFFFFC107),
                fotos = listOf(
                    "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
                    "https://images.unsplash.com/photo-1555939594-58d7cb561ad1",
                    "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38"
                ),
                latitude = -8.4186,
                longitude = -37.0538
            ),
            onVoltar = {},
            onIrHome = {},
            onIrProgramacao = {},
            onIrLive = {},
            onVerMapa = {}
        )
    }
}
