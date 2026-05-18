package ru.itis.android.uprising26

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import my.study.di.AppComponent
import my.study.di.AppComponentProvider
import my.study.di.DaggerAppComponent
import ru.itis.android.uprising26.firebase.AnalyticsReporter
import ru.itis.android.uprising26.firebase.CrashReporter

class UprisingApplication : Application(), AppComponentProvider {

    private lateinit var appComponent: AppComponent

    lateinit var crashReporter: CrashReporter
        private set

    lateinit var analyticsReporter: AnalyticsReporter
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create()

        crashReporter = CrashReporter(
            context = this
        )

        analyticsReporter = AnalyticsReporter(
            firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        )
    }

    override fun getAppComponent(): AppComponent = appComponent
}