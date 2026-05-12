package com.example.sao_joao_arcocity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.ui.theme.Sao_joao_arcocityTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Sao_joao_arcocityTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var nome by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0F14),
                        Color(0xFF05080C)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.fundo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo São João",
                modifier = Modifier
                    .width(290.dp)
                    .offset(y = (-70).dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Para começar, conta pra gente \n" +
                        "qual é seu nome:",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            BasicTextField(
                value = nome,
                onValueChange = { nome = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF1A1F25).copy(alpha = 0.85f),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(18.dp),
                decorationBox = { innerTextField ->
                    if (nome.isEmpty()) {
                        Text(
                            text = "Seu nome",
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    }

                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                Text(
                    text = "Entrar",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginPreview() {
    Sao_joao_arcocityTheme {
        LoginScreen()
    }
}