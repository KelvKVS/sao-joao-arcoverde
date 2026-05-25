package com.example.sao_joao_arcocity.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.sao_joao_arcocity.R

class EventoNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val titulo = inputData.getString("titulo") ?: return Result.success()
        val horario = inputData.getString("horario") ?: return Result.success()
        val local = inputData.getString("local") ?: return Result.success()

        val notification = NotificationCompat.Builder(applicationContext, "saojoao_channel")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Em 30 minutos: $titulo")
            .setContentText("$horario · $local")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(titulo.hashCode(), notification)
        } catch (e: SecurityException) {
            // Permissão POST_NOTIFICATIONS não concedida
        }

        return Result.success()
    }
}
