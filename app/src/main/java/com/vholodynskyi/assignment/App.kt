package com.vholodynskyi.assignment

import android.app.Application
import com.vholodynskyi.assignment.di.dataModule
import com.vholodynskyi.assignment.di.domainModule
import com.vholodynskyi.assignment.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            properties(
                mapOf("base" to "https://randomuser.me/")
            )
            val modules = listOf(dataModule, domainModule, presentationModule)
            modules(modules)
            androidContext(this@App)
        }
    }
}