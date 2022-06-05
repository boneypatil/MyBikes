package com.digikrafi.mybikes.base
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.digikrafi.mybikes.di.dashboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by rahul.p
 *
 */
class MyApplication : Application() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate() {
        super.onCreate()
        initConnectionUtil()


        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    dashboardModule
                )
            )
        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initConnectionUtil() {
        DetectConnection.initialize(this)
    }

}