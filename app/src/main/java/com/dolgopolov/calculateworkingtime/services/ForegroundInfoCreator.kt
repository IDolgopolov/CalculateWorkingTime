package com.dolgopolov.calculateworkingtime.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import com.dolgopolov.calculateworkingtime.R
import java.util.*

class ForegroundInfoCreator {

    fun createForegroundInfo(context: Context, workerId: UUID): ForegroundInfo {
        val id = context.getString(R.string.notification_channel_id)
        val title = context.getString(R.string.notification_title)
        val stop = context.getString(R.string.stop_timer)

        val intent = WorkManager.getInstance(context)
            .createCancelPendingIntent(workerId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context, id)
        }

        val notification = NotificationCompat.Builder(context, id)
            .setContentTitle(title)
            .setTicker(title)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, stop, intent)
            .build()

        return ForegroundInfo(Random().nextInt(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context, channelId: String) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val mChannel = NotificationChannel(channelId, name, importance)
        mChannel.description = descriptionText

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
}