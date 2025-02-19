package dev.drtheo.mes

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.drtheo.mes.data.AppContainer
import dev.drtheo.mes.data.DefaultAppContainer
import dev.drtheo.mes.ui.screens.DnevnikViewModel

interface ExceptionListener {
    fun uncaughtException(thread: Thread, throwable: Throwable)
}

class MainApplication : Application(), ExceptionListener {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    lateinit var dnevnikViewModel: DnevnikViewModel

    override fun onCreate() {
        super.onCreate()
        setupExceptionHandler()

        container = DefaultAppContainer(applicationContext)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        throwable.message?.let { Log.e("Dnevnik", it) }
        throwable.printStackTrace()
    }

    private fun setupExceptionHandler() {
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    uncaughtException(Looper.getMainLooper().thread, e)
                }
            }
        }

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            uncaughtException(t, e)
        }
    }
}