package com.example.sao_joao_arcocity.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sao_joao_arcocity.R
import com.example.sao_joao_arcocity.cache.LocalCache
import com.example.sao_joao_arcocity.models.ProgramacaoResponse
import com.example.sao_joao_arcocity.network.RetrofitInstance

class DiarioNotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val dia = inputData.getString("dia") ?: return Result.success()

        val texto = try {
            val programacoes = RetrofitInstance.api.buscarProgramacoes()
            LocalCache.salvar(applicationContext, "programacoes.json", programacoes)
            val doDia = programacoes.filter { it.dia == dia }
            if (doDia.isEmpty()) "Nenhum evento encontrado para hoje."
            else doDia.take(6).joinToString("\n") { "${it.horario}  ${it.titulo}" }
        } catch (e: Exception) {
            val cache = LocalCache.carregar<List<ProgramacaoResponse>>(applicationContext, "programacoes.json")
            val doDia = cache?.filter { it.dia == dia }
            if (!doDia.isNullOrEmpty())
                doDia.take(6).joinToString("\n") { "${it.horario}  ${it.titulo}" }
            else
                "Abra o app para ver a programação de hoje!"
        }

        val notification = NotificationCompat.Builder(applicationContext, "saojoao_channel")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Programação de hoje — $dia")
            .setStyle(NotificationCompat.BigTextStyle().bigText(texto))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext)
                .notify("diario_$dia".hashCode(), notification)
        } catch (e: SecurityException) {
            // Permissão POST_NOTIFICATIONS não concedida
        }

        return Result.success()
    }
}
