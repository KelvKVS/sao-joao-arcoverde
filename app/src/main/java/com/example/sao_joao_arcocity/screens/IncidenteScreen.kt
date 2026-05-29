package com.example.sao_joao_arcocity.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sao_joao_arcocity.models.IncidenteRequest
import com.example.sao_joao_arcocity.network.RetrofitInstance
import com.example.sao_joao_arcocity.ui.theme.LocalAppColors
import kotlinx.coroutines.launch

private val Vermelho = Color(0xFFEF5350)
private val VermelhoEscuro = Color(0xFF4A1010)

private val tiposIncidente = listOf(
    "Lixo acumulado",
    "Problema de seguranca",
    "Briga / Confusao",
    "Assistencia medica",
    "Infraestrutura danificada",
    "Outro"
)

private val tiposLocal = listOf(
    "Patio / Palco",
    "Rua / Calcada",
    "Area Comercial",
    "Outro local"
)

@Composable
fun IncidenteScreen(
    onVoltar: () -> Unit,
    pontoNome: String = "",
    pontoEndereco: String = ""
) {
    val colors = LocalAppColors.current
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("SaoJoaoPrefs", Context.MODE_PRIVATE) }
    val userId = remember { prefs.getString("nomeUsuario", "usuario") ?: "usuario" }
    val scope = rememberCoroutineScope()

    var tipoSelecionado by remember { mutableStateOf(tiposIncidente[0]) }
    var descricao by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf(pontoEndereco) }
    var enviando by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.fundo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 48.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(colors.card)
                        .clickable { onVoltar() }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = "< Voltar", color = colors.textoPrimario, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Reportar Problema", color = Vermelho, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            if (pontoNome.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Local: $pontoNome", color = colors.textoSecundario, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Tipo do problema", color = colors.textoPrimario, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                tiposIncidente.forEach { tipo ->
                    val ativo = tipoSelecionado == tipo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (ativo) VermelhoEscuro else colors.card)
                            .border(1.dp, if (ativo) Vermelho else colors.divisor, RoundedCornerShape(12.dp))
                            .clickable { tipoSelecionado = tipo }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = tipo,
                            color = if (ativo) Vermelho else colors.textoPrimario,
                            fontWeight = if (ativo) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Endereco / Referencia", color = colors.textoPrimario, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            if (pontoNome.isBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tiposLocal.forEach { local ->
                        val prefixo = when (local) {
                            "Patio / Palco" -> "Patio/Palco — "
                            "Rua / Calcada" -> "Rua — "
                            "Area Comercial" -> "Area comercial — "
                            else -> ""
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(colors.card)
                                .border(1.dp, colors.divisor, RoundedCornerShape(10.dp))
                                .clickable { endereco = prefixo }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = local,
                                color = colors.textoPrimario,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 2,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            BasicTextField(
                value = endereco,
                onValueChange = { endereco = it },
                textStyle = TextStyle(color = colors.textoPrimario, fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.card, RoundedCornerShape(12.dp))
                    .padding(14.dp),
                decorationBox = { inner ->
                    if (endereco.isEmpty()) Text("Ex: Praca principal, proximo ao palco", color = colors.textoSecundario, fontSize = 14.sp)
                    inner()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Descricao (opcional)", color = colors.textoPrimario, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = descricao,
                onValueChange = { descricao = it },
                textStyle = TextStyle(color = colors.textoPrimario, fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colors.card, RoundedCornerShape(12.dp))
                    .padding(14.dp),
                decorationBox = { inner ->
                    if (descricao.isEmpty()) Text("Descreva o problema com mais detalhes...", color = colors.textoSecundario, fontSize = 14.sp)
                    inner()
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            resultado?.let { msg ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (msg.startsWith("Reportado")) Color(0xFF1B3A1B) else VermelhoEscuro)
                        .padding(14.dp)
                ) {
                    Text(text = msg, color = if (msg.startsWith("Reportado")) Color(0xFF81C784) else Vermelho, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (enviando || endereco.isBlank()) Color.Gray else Vermelho)
                    .clickable(enabled = !enviando && endereco.isNotBlank()) {
                        enviando = true
                        scope.launch {
                            try {
                                RetrofitInstance.api.registrarIncidente(
                                    IncidenteRequest(
                                        tipo = tipoSelecionado,
                                        descricao = descricao.ifBlank { tipoSelecionado },
                                        endereco = endereco,
                                        userId = userId
                                    )
                                )
                                resultado = "Reportado com sucesso! A organizacao foi notificada."
                                descricao = ""
                            } catch (e: Exception) {
                                resultado = "Erro ao enviar. Verifique sua conexao."
                            } finally {
                                enviando = false
                            }
                        }
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (enviando) "Enviando..." else "Reportar Problema",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
