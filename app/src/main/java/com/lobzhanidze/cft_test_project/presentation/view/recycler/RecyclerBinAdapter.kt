package com.lobzhanidze.cft_test_project.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lobzhanidze.cft_test_project.R
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory

class RecyclerBinAdapter(
    private val inflater: LayoutInflater,
    private val listener: OnClickListenerBin
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<QueryBinHistory>()

    fun setItems(listQuery: MutableList<QueryBinHistory>) {
        items.clear()
        items.addAll(listQuery)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerBinHolder(inflater.inflate(R.layout.query_bin_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerBinHolder) {
            val item = items[position]
            holder.bind(item)
            initListener(holder, position)
        }
    }

    override fun getItemCount() = items.size

    private fun initListener(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onBinClick(items[position], position)
        }
    }

    interface OnClickListenerBin {
        fun onBinClick(binHistory: QueryBinHistory, position: Int)
    }
}