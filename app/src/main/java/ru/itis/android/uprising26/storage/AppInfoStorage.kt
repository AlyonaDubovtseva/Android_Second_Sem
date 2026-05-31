package ru.itis.android.uprising26.storage

import android.content.Context

class AppInfoStorage(
    context: Context
) {

    private val prefs = context.getSharedPreferences(
        "app_info_storage",
        Context.MODE_PRIVATE
    )

    fun shouldShowAppInfo(): Boolean {
        return prefs.getBoolean(KEY_APP_INFO_ACCEPTED, false).not()
    }

    fun markAppInfoAccepted() {
        prefs.edit()
            .putBoolean(KEY_APP_INFO_ACCEPTED, true)
            .apply()
    }

    companion object {
        private const val KEY_APP_INFO_ACCEPTED = "app_info_accepted"
    }
}