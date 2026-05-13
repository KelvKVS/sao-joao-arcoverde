package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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

data class DiaEvento(
    val dia: String,
    val semana: String,
    val ativo: Boolean = false
)

@Composable
fun HomeScreen(
    nome: String,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit
) {

    val dias = listOf(
        DiaEvento("21 JUN", "Sexta", true),
        DiaEvento("21 JUN", "Sabado"),
        DiaEvento("21 JUN", "Domingo"),
        DiaEvento("21 JUN", "Segunda")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05080C))
    ) {

        Image(
            painter = painterResource(id = R.drawable.fundohome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
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
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column {

                    Text(
                        text = "Olá, $nome",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Bem-vindo ao",
                        color = Color.LightGray,
                        fontSize = 15.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.width(185.dp)
                )

                Spacer(modifier = Modifier.width(18.dp))

                Column {

                    Text(
                        text = "☆ Patrocinadores",
                        color = Color(0xFFFFC107),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Apoiadores que fazem o são\njoão acontecer!",
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {

                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.calendarcheck),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Programação",
                        color = Color(0xFFFFC107),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Acompanhe as atrações dos dias de festa!",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {

                items(dias) { dia ->

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(95.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (dia.ativo)
                                    Color(0xFFFFC107)
                                else
                                    Color.Transparent
                            )
                            .border(
                                width = if (dia.ativo) 0.dp else 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = dia.dia,
                                color = if (dia.ativo) Color.Black else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = dia.semana,
                                color = if (dia.ativo) Color.Black else Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF07101D))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.show),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(95.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(18.dp))
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "21 de Junho - sexta",
                            color = Color(0xFFFFC107),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        repeat(4) {

                            Text(
                                text = "19:00 abertura oficial",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(
                                1.dp,
                                Color(0xFFFFC107),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = ">",
                            color = Color(0xFFFFC107),
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),

            telaAtual = "home",

            onHomeClick = { },

            onProgramacaoClick = {
                onIrProgramacao()
            },

            onLiveClick = {
                onIrLive()
            }
        )
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,

    telaAtual: String,

    onHomeClick: () -> Unit,

    onProgramacaoClick: () -> Unit,

    onLiveClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(82.dp)
            .background(
                Color(0xCC0B0F14)
            )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomItem(
                icon = R.drawable.home,
                title = "Home",
                ativo = telaAtual == "home",
                onClick = onHomeClick
            )

            BottomItem(
                icon = R.drawable.calendar,
                title = "Programação",
                ativo = telaAtual == "programacao",
                onClick = onProgramacaoClick
            )

            BottomItem(
                icon = R.drawable.live,
                title = "Live",
                ativo = telaAtual == "live",
                onClick = onLiveClick
            )

            BottomItem(
                icon = R.drawable.location,
                title = "Pontos",
                ativo = false,
                onClick = { }
            )

            BottomItem(
                icon = R.drawable.info,
                title = "Sobre",
                ativo = false,
                onClick = { }
            )
        }
    }
}

@Composable
fun BottomItem(
    icon: Int,
    title: String,
    ativo: Boolean = false,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.clickable {
            onClick()
        }
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(26.dp),
            colorFilter = ColorFilter.tint(
                if (ativo)
                    Color(0xFFFFC107)
                else
                    Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = if (ativo)
                Color(0xFFFFC107)
            else
                Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomePreview() {

    Sao_joao_arcocityTheme {

        HomeScreen(
            nome = "usuario",
            onIrProgramacao = {},
            onIrLive = {}
        )
    }
}