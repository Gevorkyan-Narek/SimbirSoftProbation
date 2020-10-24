package com.cyclone.simbirsoftprobation.presentation.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cyclone.simbirsoftprobation.R
import com.cyclone.simbirsoftprobation.domain.model.Event
import com.cyclone.simbirsoftprobation.domain.utilities.MyUtils
import com.cyclone.simbirsoftprobation.domain.utilities.loadDrawable
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private var filteredEvents: MutableList<Event>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.news_avatar
        val title: TextView = itemView.news_title
        val content: TextView = itemView.content
        val date: TextView = itemView.date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = filteredEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.loadDrawable(holder.itemView.context, filteredEvents[position].photos[0])
        holder.title.text = filteredEvents[position].name
        holder.content.text = filteredEvents[position].shortDescription
        holder.date.text = MyUtils.getRelevance(filteredEvents[position])
        holder.itemView.setOnClickListener {
            val detailActivity = Intent(it.context, DetailActivity::class.java)
            detailActivity.putExtra("event_id", filteredEvents[position].id)
            it.context.startActivity(detailActivity)
        }
    }
}