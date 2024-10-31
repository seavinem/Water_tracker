package com.example.watertracker.screen.fragment.statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.watertracker.R
import com.example.watertracker.data.source.database.WaterEntity

class StatisticAdapter : RecyclerView.Adapter<StatisticItemViewHolder>() {

    private val list: MutableList<WaterEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_statistic, parent, false)

        return StatisticItemViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StatisticItemViewHolder, position: Int) {
        val item = list[position]
        holder.textViewAmount.text = item.amount.toString()
        holder.textViewDate.text = item.createdDate
    }

    fun setData(items: List<WaterEntity>) {
        list.clear()
        list.addAll(items)
        notifyItemInserted(0)
    }

}