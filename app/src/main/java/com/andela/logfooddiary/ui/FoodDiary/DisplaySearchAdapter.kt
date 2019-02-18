package com.andela.logfooddiary.ui.FoodDiary

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Food
import com.andela.logfooddiary.databinding.SingleFoodLogBinding


class DisplaySearchAdapter : RecyclerView.Adapter<DisplaySearchAdapter.DisplaySearchLogViewHolder>() {

    private var resultItems = mutableListOf<Food>()

    fun updateList(update: List<Food>) {
        resultItems.clear()
        resultItems.addAll(update)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DisplaySearchLogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleFoodLogBinding>(
                inflater, R.layout.single_food_log, parent, false
        )
        return DisplaySearchLogViewHolder(binding)
    }

    override fun getItemCount(): Int = resultItems.size

    override fun onBindViewHolder(holder: DisplaySearchLogViewHolder, position: Int) {
        val item = resultItems[position]
        holder.bind(item)
    }

    inner class DisplaySearchLogViewHolder(val binding: SingleFoodLogBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind( item: Food) {
            binding.item = item
        }

    }
}