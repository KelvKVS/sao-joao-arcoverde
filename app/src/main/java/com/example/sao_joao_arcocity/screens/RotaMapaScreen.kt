package com.example.sao_joao_arcocity.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

@Composable
fun RotaMapaScreen(
    ponto: PontoCidade,
    onFechar: () -> Unit
) {
    val context = LocalContext.current

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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rota até o destino",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                        .clickable { onFechar() }
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "×",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            CampoRota(
                texto = "Meu local",
                icone = R.drawable.ellipse
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoRota(
                texto = ponto.nome,
                icone = R.drawable.gps
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ChipTempo(
                    texto = "5 min",
                    icone = R.drawable.car,
                    ativo = true
                )

                ChipTempo(
                    texto = "10 min",
                    icone = R.drawable.bike
                )

                ChipTempo(
                    texto = "18 min",
                    icone = R.drawable.manwalking
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFE9EEF4))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mapa),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "5 min (1,5 km)",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Trajeto mais rápido",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = {
                    val uri = Uri.parse(
                        "google.navigation:q=${ponto.latitude},${ponto.longitude}"
                    )

                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.maps),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Iniciar no Google Maps",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CampoRota(
    texto: String,
    icone: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icone),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.45f),
                    RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = texto,
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ChipTempo(
    texto: String,
    icone: Int,
    ativo: Boolean = false
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (ativo)
                    Color(0xFFBBD6FF)
                else
                    Color.Transparent
            )
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icone),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = texto,
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
fun RotaMapaPreview() {
    Sao_joao_arcocityTheme {
        RotaMapaScreen(
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
            onFechar = {}
        )
    }
}