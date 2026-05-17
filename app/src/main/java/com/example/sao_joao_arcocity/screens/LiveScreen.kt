package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

data class LiveEvento(
    val horario: String,
    val titulo: String,
    val local: String,
    val imagem: Int
)

@Composable
fun LiveScreen(
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrPontos: () -> Unit
) {

    val eventos = listOf(

        LiveEvento(
            horario = "19:00",
            titulo = "Trio Pé de Serra",
            local = "Palco Principal",
            imagem = R.drawable.trio
        ),

        LiveEvento(
            horario = "20:00",
            titulo = "Forró dos Bons",
            local = "Palco Principal",
            imagem = R.drawable.forro
        ),

        LiveEvento(
            horario = "21:00",
            titulo = "João Gomes",
            local = "Palco Principal",
            imagem = R.drawable.joaogomes
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
                .padding(bottom = 90.dp)
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Transmissão ao vivo",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Acompanhe a festa em tempo real!",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {

                Image(
                    painter = painterResource(id = R.drawable.livebanner),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .padding(14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Red)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                ) {

                    Text(
                        text = "● Ao vivo",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(14.dp)
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color.Red),

                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Próximas atrações",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            eventos.forEach { evento ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = evento.horario,
                            color = Color(0xFFFFC107),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "Sexta",
                            color = Color.LightGray,
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

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = evento.titulo,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = evento.local,
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 20.dp)
                        .background(Color.White.copy(alpha = 0.15f))
                )

                Spacer(modifier = Modifier.height(18.dp))
            }
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),

            telaAtual = "live",

            onHomeClick = {
                onIrHome()
            },

            onProgramacaoClick = {
                onIrProgramacao()
            },

            onLiveClick = { },

            onpontosClick = {
                onIrPontos()
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LivePreview() {

    Sao_joao_arcocityTheme {

        LiveScreen(
            onIrHome = {},
            onIrProgramacao = {},
            onIrPontos = {}
        )
    }
}