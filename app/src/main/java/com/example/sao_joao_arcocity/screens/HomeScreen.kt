package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.models.BannerResponse
import com.example.sao_joao_arcocity.network.RetrofitInstance
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme
import kotlinx.coroutines.delay

data class DiaEvento(
    val dia: String,
    val semana: String,
    val ativo: Boolean = false
)

data class BannerHome(
    val titulo: String,
    val subtitulo: String,
    val imagem: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    nome: String,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onIrPontos: () -> Unit,
    onIrSobre: () -> Unit
) {
    val dias = listOf(
        DiaEvento("21 JUN", "Sexta", true),
        DiaEvento("22 JUN", "Sabado"),
        DiaEvento("23 JUN", "Domingo"),
        DiaEvento("24 JUN", "Segunda")
    )

    var banners by remember {
        mutableStateOf<List<BannerHome>>(emptyList())
    }

    LaunchedEffect(Unit) {
        try {
            val resposta = RetrofitInstance.api.buscarBanners()
            banners = resposta.map { it.toBannerHome() }
        } catch (e: Exception) {
            banners = listOf(
                BannerHome(
                    titulo = "São João Arco City",
                    subtitulo = "O maior São João da região!",
                    imagem = R.drawable.banner
                )
            )
        }
    }

    val bannersExibidos =
        if (banners.isEmpty()) {
            listOf(
                BannerHome(
                    titulo = "São João Arco City",
                    subtitulo = "Carregando banners...",
                    imagem = R.drawable.banner
                )
            )
        } else {
            banners
        }

    val pagerState = rememberPagerState(
        pageCount = { bannersExibidos.size }
    )

    LaunchedEffect(bannersExibidos.size) {
        while (bannersExibidos.size > 1) {
            delay(3000)

            val nextPage =
                if (pagerState.currentPage == bannersExibidos.lastIndex) 0
                else pagerState.currentPage + 1

            pagerState.animateScrollToPage(nextPage)
        }
    }

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

            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    pageSpacing = 14.dp
                ) { page ->

                    val banner = bannersExibidos[page]

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        Image(
                            painter = painterResource(id = banner.imagem),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.18f))
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(18.dp)
                        ) {
                            Text(
                                text = banner.titulo,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = banner.subtitulo,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(bannersExibidos.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(
                                    if (pagerState.currentPage == index) 10.dp else 7.dp
                                )
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        Color(0xFFFFC107)
                                    else
                                        Color.White.copy(alpha = 0.35f)
                                )
                        )
                    }
                }
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
                                if (dia.ativo) Color(0xFFFFC107)
                                else Color.Transparent
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
                            )
                            .clickable {
                                onIrProgramacao()
                            },
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
            onHomeClick = {},
            onProgramacaoClick = { onIrProgramacao() },
            onLiveClick = { onIrLive() },
            onpontosClick = { onIrPontos() },
            onSobreClick = { onIrSobre() }
        )
    }
}

fun BannerResponse.toBannerHome(): BannerHome {
    return BannerHome(
        titulo = titulo,
        subtitulo = subtitulo,
        imagem = escolherImagemBanner(imagem)
    )
}

fun escolherImagemBanner(imagem: String): Int {
    return when (imagem.lowercase()) {
        "banner.png" -> R.drawable.banner
        "show.png" -> R.drawable.show
        "forro.png" -> R.drawable.forro
        "trio.png" -> R.drawable.trio
        else -> R.drawable.banner
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    telaAtual: String,
    onHomeClick: () -> Unit,
    onProgramacaoClick: () -> Unit,
    onLiveClick: () -> Unit,
    onpontosClick: () -> Unit,
    onSobreClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(82.dp)
            .background(Color(0xCC0B0F14))
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
                ativo = telaAtual == "pontos",
                onClick = onpontosClick
            )

            BottomItem(
                icon = R.drawable.info,
                title = "Sobre",
                ativo = telaAtual == "sobre",
                onClick = onSobreClick
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
                if (ativo) Color(0xFFFFC107)
                else Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = if (ativo) Color(0xFFFFC107) else Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    Sao_joao_arcocityTheme {
        HomeScreen(
            nome = "usuario",
            onIrProgramacao = {},
            onIrLive = {},
            onIrPontos = {},
            onIrSobre = {}
        )
    }
}