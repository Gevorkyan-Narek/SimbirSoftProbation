package com.cyclone.simbirsoftprobation.Presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cyclone.simbirsoftprobation.R
import kotlinx.android.synthetic.main.search_result_item.view.*

class SearchResultsAdapter(var results: MutableList<String>) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultName = itemView.result_name
        val separator = itemView.separator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultName.text = results[position]
        if(position == results.lastIndex) holder.separator.visibility = View.INVISIBLE
    }
}