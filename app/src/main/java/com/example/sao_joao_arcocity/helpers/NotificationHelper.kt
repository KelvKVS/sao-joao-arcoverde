package com.example.sao_joao_arcocity.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.sao_joao_arcocity.screens.EventoProgramacao
import com.example.sao_joao_arcocity.workers.DiarioNotificationWorker
import com.example.sao_joao_arcocity.workers.EventoNotificationWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit

private val MESES = mapOf(
    "JAN" to Calendar.JANUARY, "FEV" to Calendar.FEBRUARY, "MAR" to Calendar.MARCH,
    "ABR" to Calendar.APRIL,   "MAI" to Calendar.MAY,      "JUN" to Calendar.JUNE,
    "JUL" to Calendar.JULY,    "AGO" to Calendar.AUGUST,   "SET" to Calendar.SEPTEMBER,
    "OUT" to Calendar.OCTOBER, "NOV" to Calendar.NOVEMBER, "DEZ" to Calendar.DECEMBER
)

fun criarCanalNotificacao(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val canal = NotificationChannel(
            "saojoao_channel",
            "São João Notificações",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Alertas de eventos e programação diária" }
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(canal)
    }
}

private fun calcularDelayMs(dia: String, horario: String, minutosAntes: Long = 0): Long {
    val partesDia = dia.split(" ")
    if (partesDia.size < 2) return -1
    val mes = MESES[partesDia[1]] ?: return -1
    val partesHora = horario.split(":")
    if (partesHora.size < 2) return -1

    val cal = Calendar.getInstance().apply {
        set(Calendar.MONTH, mes)
        set(Calendar.DAY_OF_MONTH, partesDia[0].toInt())
        set(Calendar.HOUR_OF_DAY, partesHora[0].toInt())
        set(Calendar.MINUTE, partesHora[1].toInt())
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return cal.timeInMillis - System.currentTimeMillis() - (minutosAntes * 60_000L)
}

fun agendarEventoFavorito(context: Context, evento: EventoProgramacao) {
    val delayMs = calcularDelayMs(evento.dia, evento.horario, minutosAntes = 30)
    if (delayMs <= 0) return

    val request = OneTimeWorkRequestBuilder<EventoNotificationWorker>()
        .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
        .setInputData(workDataOf(
            "titulo"  to evento.titulo,
            "horario" to evento.horario,
            "local"   to evento.local
        ))
        .addTag("favorito_evento")
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "fav_${evento.id}",
        ExistingWorkPolicy.REPLACE,
        request
    )
}

fun reagendarFavoritos(context: Context, favoritos: Set<String>, eventos: List<EventoProgramacao>) {
    WorkManager.getInstance(context).cancelAllWorkByTag("favorito_evento")
    eventos.filter { it.id in favoritos }.forEach { agendarEventoFavorito(context, it) }
}

fun agendarResumoDiario(context: Context) {
    val diasFestival = listOf("21 JUN", "22 JUN", "23 JUN", "24 JUN")
    diasFestival.forEach { dia ->
        val delayMs = calcularDelayMs(dia, "17:00")
        if (delayMs <= 0) return@forEach

        val request = OneTimeWorkRequestBuilder<DiarioNotificationWorker>()
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("dia" to dia))
            .addTag("diario_resumo")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "diario_$dia",
            ExistingWorkPolicy.KEEP,
            request
        )
    }
}
