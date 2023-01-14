package com.lobzhanidze.cft_test_project.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory

@Dao
interface HistoryQueryBinDao {
    @Insert(onConflict = IGNORE)
    fun setQuery(query: QueryBinHistory)

    @Query("select * from QUERY_BIN_HISTORY")
    fun getQueryHistory():MutableList<QueryBinHistory>
}