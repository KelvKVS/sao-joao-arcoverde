package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

data class DevSobre(
    val nome: String,
    val imagem: Int
)

@Composable
fun SobreScreen(
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onIrPontos: () -> Unit
) {
    val devs = listOf(
        DevSobre("Intalou", R.drawable.italo),
        DevSobre("Kelvis duran", R.drawable.kelvis),
        DevSobre("Sabrina sato", R.drawable.sabrina),
        DevSobre("reed richards", R.drawable.reed),
        DevSobre("Igor fina", R.drawable.igor),
        DevSobre("Jerfino", R.drawable.jerfino)
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
                .padding(horizontal = 28.dp)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(55.dp))

            Image(
                painter = painterResource(id = R.drawable.sobre),
                contentDescription = null,
                modifier = Modifier
                    .width(220.dp)
                    .height(90.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(28.dp))

            SecaoTitulo(
                icone = "ⓘ",
                titulo = "Sobre o projeto"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "O São João de ArcoCity foi desenvolvido para aproximar a população da programação do São João de Arcoverde, além de destacar os principais pontos da cidade e transmitir ao vivo os melhores momentos da festa.",
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(42.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.peoples),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Desenvolvedores",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                devs.chunked(3).forEach { linha ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        linha.forEach { dev ->
                            DevItem(dev = dev)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.peoples),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Orientador",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Image(
                painter = painterResource(id = R.drawable.professor),
                contentDescription = null,
                modifier = Modifier
                    .height(90.dp)
                    .width(70.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "William S De Jesus",
                color = Color.White,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(38.dp))

            Image(
                painter = painterResource(id = R.drawable.aesa),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            telaAtual = "sobre",
            onHomeClick = onIrHome,
            onProgramacaoClick = onIrProgramacao,
            onLiveClick = onIrLive,
            onpontosClick = onIrPontos,
            onSobreClick = {}
        )
    }
}

@Composable
fun SecaoTitulo(
    icone: String,
    titulo: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icone,
            color = Color(0xFFFFC107),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = titulo,
            color = Color(0xFFFFC107),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DevItem(
    dev: DevSobre
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.chapeu),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = dev.imagem),
            contentDescription = null,
            modifier = Modifier
                .height(70.dp)
                .width(55.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = dev.nome,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SobrePreview() {
    Sao_joao_arcocityTheme {
        SobreScreen(
            onIrHome = {},
            onIrProgramacao = {},
            onIrLive = {},
            onIrPontos = {}
        )
    }
}