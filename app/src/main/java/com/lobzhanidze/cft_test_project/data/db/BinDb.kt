package com.lobzhanidze.cft_test_project.data.db

import android.content.Context
import androidx.room.Room
import com.lobzhanidze.cft_test_project.AppDb

object BinDb {
    private var INSTANCE: AppDb? = null

    fun getInstance(context: Context): AppDb? {
        if (INSTANCE == null) {
            synchronized(AppDb::class) {
                INSTANCE = Room.databaseBuilder(context, AppDb::class.java, "binDb").build()
            }
        }
        return INSTANCE
    }

    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }
}