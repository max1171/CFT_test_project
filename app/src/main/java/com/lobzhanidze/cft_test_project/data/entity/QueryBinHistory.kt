package com.lobzhanidze.cft_test_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "query_bin_history")
data class QueryBinHistory(
    @PrimaryKey(autoGenerate = true)
    var idHistory: Int,
    val dateQuery: String,
    val binQuery: String
) {
    constructor(dateQuery: String, binQuery: String):this (0, dateQuery, binQuery)
}