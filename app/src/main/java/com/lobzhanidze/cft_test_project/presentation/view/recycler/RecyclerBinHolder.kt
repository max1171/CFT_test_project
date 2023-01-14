package com.lobzhanidze.cft_test_project.presentation.view.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lobzhanidze.cft_test_project.R
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory

class RecyclerBinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dateQuery: TextView = itemView.findViewById(R.id.date_query)
    private val binQuery: TextView = itemView.findViewById(R.id.bin_query)

    fun bind(item: QueryBinHistory){
        dateQuery.text = item.dateQuery
        binQuery.text = item.binQuery
    }
}