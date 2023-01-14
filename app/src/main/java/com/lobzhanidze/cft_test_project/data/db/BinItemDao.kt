package com.lobzhanidze.cft_test_project.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.android.material.circularreveal.CircularRevealHelper
import com.lobzhanidze.cft_test_project.data.entity.BinModelDomain

@Dao
interface BinItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBinItem(bin: BinModelDomain)

    @Query("select * from bin_query")
    fun getBinItem(): BinModelDomain

    @Query("delete from bin_query")
    suspend fun clearTable()
}