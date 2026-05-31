package ru.itis.android.uprising26.firebase

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.util.UUID

class CrashReporter(
    context: Context
) {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    private val userId: String = getOrCreateUserId(context)

    init {
        crashlytics.setUserId(userId)
        crashlytics.setCustomKey("user_id", userId)
    }

    fun logScreen(screenName: String) {
        crashlytics.log("Screen opened: $screenName")
        crashlytics.setCustomKey("last_screen", screenName)
    }

    fun logClick(action: String) {
        crashlytics.log("Click: $action")
    }

    fun recordException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    private fun getOrCreateUserId(context: Context): String {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val existingId = prefs.getString("user_id", null)
        if (existingId != null) return existingId

        val newId = UUID.randomUUID().toString()

        prefs.edit()
            .putString("user_id", newId)
            .apply()

        return newId
    }
}