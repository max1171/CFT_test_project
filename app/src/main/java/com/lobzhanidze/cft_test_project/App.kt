package com.lobzhanidze.cft_test_project

import android.app.Application
import com.lobzhanidze.cft_test_project.data.db.BinDb
import com.lobzhanidze.cft_test_project.dugger.BinComponent
import com.lobzhanidze.cft_test_project.dugger.DaggerBinComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class App : Application() {
    val binComponent: BinComponent = DaggerBinComponent.builder().build()

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        binComponent.inject(this)
        instance = this
    }

    @Inject
    fun initDb() {
        Executors.newSingleThreadScheduledExecutor().execute(
            Runnable {
                BinDb.getInstance(this)?.getBinItemDao()?.getBinItem()
            }
        )
    }
}