package ru.itis.android.uprising26.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsReporter(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun logAppInfoShown() {
        firebaseAnalytics.logEvent(
            "app_info_shown",
            Bundle()
        )
    }

    fun logAppInfoClosed() {
        firebaseAnalytics.logEvent(
            "app_info_closed",
            Bundle()
        )
    }

    fun logScreenOpened(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }

        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            params
        )
    }
}