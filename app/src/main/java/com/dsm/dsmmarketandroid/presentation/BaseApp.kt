package com.dsm.dsmmarketandroid.presentation

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.dsm.dsmmarketandroid.di.*
import com.dsm.dsmmarketandroid.presentation.util.LocaleManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class BaseApp : Application() {

    companion object {
        var localeManager: LocaleManager? = null
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApp)
            modules(
                listOf(
                    dataSourceModule,
                    localModule,
                    mapperModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    pagingModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager?.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager?.setLocale(this)
    }

    open fun getApiUrl() = "https://dsm-market.ga/"
}