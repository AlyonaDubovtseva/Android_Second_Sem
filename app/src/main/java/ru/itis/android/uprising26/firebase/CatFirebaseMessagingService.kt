package ru.itis.android.uprising26.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.itis.android.uprising26.R

class CatFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM_DATA", "raw data = ${message.data}")
        val data = message.data

        val kind = data[KEY_KIND] ?: KIND_DEFAULT
        val title = data[KEY_TITLE] ?: getString(R.string.notification_default_title)
        val body = data[KEY_MESSAGE] ?: getString(R.string.notification_default_message)

        Log.d("FCM_DATA", "kind=$kind title=$title body=$body")

        showNotification(
            kind = kind,
            title = title,
            body = body
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CrashReporter(applicationContext).logClick("FCM token updated")
        CrashReporter(applicationContext).logClick(token)
    }

    private fun showNotification(
        kind: String,
        title: String,
        body: String
    ) {
        val channelId = getChannelIdByKind(kind)

        createNotificationChannel(
            channelId = channelId,
            kind = kind
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(this).notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }

    private fun getChannelIdByKind(kind: String): String {
        return when (kind) {
            KIND_PROMO -> CHANNEL_PROMO
            KIND_AUTH -> CHANNEL_AUTH
            KIND_NEWS -> CHANNEL_NEWS
            else -> CHANNEL_DEFAULT
        }
    }

    private fun createNotificationChannel(
        channelId: String,
        kind: String
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelName = getChannelName(kind)
        val channelDescription = getChannelDescription(kind)

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = channelDescription
        }

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun getChannelName(kind: String): String {
        return when (kind) {
            KIND_PROMO -> getString(R.string.notification_channel_promo_name)
            KIND_AUTH -> getString(R.string.notification_channel_auth_name)
            KIND_NEWS -> getString(R.string.notification_channel_news_name)
            else -> getString(R.string.notification_channel_default_name)
        }
    }

    private fun getChannelDescription(kind: String): String {
        return when (kind) {
            KIND_PROMO -> getString(R.string.notification_channel_promo_description)
            KIND_AUTH -> getString(R.string.notification_channel_auth_description)
            KIND_NEWS -> getString(R.string.notification_channel_news_description)
            else -> getString(R.string.notification_channel_default_description)
        }
    }

    companion object {
        private const val KEY_KIND = "kind"
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"

        private const val KIND_PROMO = "promo"
        private const val KIND_AUTH = "auth"
        private const val KIND_NEWS = "news"
        private const val KIND_DEFAULT = "default"

        private const val CHANNEL_PROMO = "promo_channel"
        private const val CHANNEL_AUTH = "auth_channel"
        private const val CHANNEL_NEWS = "news_channel"
        private const val CHANNEL_DEFAULT = "default_channel"
    }
}