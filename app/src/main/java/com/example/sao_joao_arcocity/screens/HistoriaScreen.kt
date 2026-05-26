package com.example.sao_joao_arcocity.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R

@Composable
fun HistoriaScreen(
    onIrHome: () -> Unit,
    onIrProgramacao: () -> Unit,
    onIrLive: () -> Unit,
    onIrPontos: () -> Unit,
    onIrSobre: () -> Unit
) {
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1F25))
                        .clickable { onIrHome() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "<",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "História do São João",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Cultura e tradição do povo nordestino",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            HistoriaSecao(
                titulo = "🏙️ A Cidade de Arcoverde",
                conteudo = "Arcoverde é um município do sertão de Pernambuco, localizado na Região Metropolitana do Vale do Ipanema, a cerca de 245 km do Recife. Com aproximadamente 77 mil habitantes, é conhecida como a \"Princesa do Sertão\" pela sua importância econômica e cultural.\n\nFundada oficialmente em 1909, a cidade tem sua história marcada pela cultura sertaneja, pelas lutas pela água e pela valorização das tradições nordestinas. O nome Arcoverde homenageia o Cardeal Joaquim Arcoverde, primeiro cardeal das Américas."
            )

            HistoriaSecao(
                titulo = "🎉 O São João de Arcoverde",
                conteudo = "O São João de Arcoverde é reconhecido como um dos maiores e mais tradicionais festejos juninos do Brasil. A festa reúne milhares de visitantes de todo o país, que vêm vivenciar o autêntico São João do sertão nordestino.\n\nO evento acontece todos os anos em junho, com vários dias de programação intensa: shows de grandes artistas do forró, quadrilhas juninas, barracas típicas e muito mais. A cidade se transforma em um grande palco a céu aberto, com polos espalhados por toda a região central.\n\nAo longo das décadas, o São João de Arcoverde cresceu de uma festa popular local para um evento de repercussão nacional, mantendo sempre o charme e a autenticidade das festas juninas do Nordeste."
            )

            HistoriaSecao(
                titulo = "🎵 Forró: A Alma do São João",
                conteudo = "O forró é o ritmo que pulsa no coração das festas juninas nordestinas. Criado e popularizado pelo genial Luiz Gonzaga — o Rei do Baião —, o forró representa a identidade musical do sertão nordestino.\n\nLuiz Gonzaga nasceu em Exu, Pernambuco, e suas músicas retratam com poesia a vida, os costumes, as alegrias e as saudades do povo sertanejo. \"Asa Branca\", \"Baião\" e \"Xote das Meninas\" se tornaram hinos que atravessam gerações.\n\nNo São João de Arcoverde, o forró é onipresente: nas ruas, nas barracas, nos shows e nas quadrilhas."
            )

            HistoriaSecao(
                titulo = "💃 Tradições e Cultura",
                conteudo = "As festas juninas são repletas de tradições que passam de geração em geração:\n\n• Quadrilha Junina — A dança típica com casais fantasiados de noivos e matutos. As quadrilhas de Arcoverde competem com apresentações elaboradas e cheias de energia.\n\n• Forró Pé de Serra — O forró original, tocado ao vivo com sanfona, triângulo e zabumba.\n\n• Comidas Típicas — Pamonha, canjica, milho assado, bolo de milho, quentão e muito mais!\n\n• Fogueiras — Símbolo da festa, representam a devoção aos santos: Santo Antônio (13/06), São João (24/06) e São Pedro (29/06).\n\n• Vaquejada — Tradição nordestina que celebra a cultura do vaqueiro e do gado no sertão."
            )

            HistoriaSecao(
                titulo = "⭐ Curiosidades",
                conteudo = "• Arcoverde é chamada de \"Portal do Sertão\", sendo ponto estratégico para o interior pernambucano.\n\n• O São João de Arcoverde recebe turistas de todo o Brasil em junho, movimentando a economia local e regional.\n\n• O evento já recebeu artistas consagrados do forró e do axé music ao longo de sua história.\n\n• As bandeiras coloridas que enfeitam as ruas durante o São João são uma das marcas mais visuais da festa, representando alegria e fartura.\n\n• O mês de junho em Arcoverde é praticamente todo dedicado às festas juninas, com eventos culturais, exposições e apresentações durante todo o mês."
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        BottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            telaAtual = "historia",
            onHomeClick = onIrHome,
            onProgramacaoClick = onIrProgramacao,
            onLiveClick = onIrLive,
            onpontosClick = onIrPontos,
            onSobreClick = onIrSobre
        )
    }
}

@Composable
private fun HistoriaSecao(titulo: String, conteudo: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF101826))
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Text(
            text = titulo,
            color = Color(0xFFFFC107),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = conteudo,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp,
            lineHeight = 22.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}
