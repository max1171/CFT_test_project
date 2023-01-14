package com.lobzhanidze.cft_test_project

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lobzhanidze.cft_test_project.data.db.BinItemDao
import com.lobzhanidze.cft_test_project.data.db.HistoryQueryBinDao
import com.lobzhanidze.cft_test_project.data.entity.BinModelDomain
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory

@Database(entities = [BinModelDomain::class, QueryBinHistory::class], version = 1)
abstract class AppDb: RoomDatabase() {
    abstract fun getBinItemDao(): BinItemDao
    abstract fun getHistoryQueryBinDao(): HistoryQueryBinDao
}