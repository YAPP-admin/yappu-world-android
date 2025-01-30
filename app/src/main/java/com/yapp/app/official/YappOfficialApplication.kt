package com.yapp.app.official

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class YappOfficialApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    // 앱 기본 폰트 크기를 1.0f로 고정합니다.
    override fun attachBaseContext(base: Context?) {
        val configuration = Configuration(base?.resources?.configuration)
        configuration.fontScale = 1.0f
        val context = base?.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }
}