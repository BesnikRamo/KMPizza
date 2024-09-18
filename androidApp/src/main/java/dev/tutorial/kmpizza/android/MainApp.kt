package dev.tutorial.kmpizza.android

import android.app.Application
import initKoin
import org.koin.android.ext.koin.androidContext

@Suppress("unused")
public class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}