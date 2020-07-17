package com.cyclone.simbirsoftprobation.Presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cyclone.simbirsoftprobation.Model.Event
import com.cyclone.simbirsoftprobation.R
import kotlinx.android.synthetic.main.item_news.view.*
import org.threeten.bp.format.DateTimeFormatter

class NewsAdapter(events: MutableList<Event>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.news_avatar
        val title: TextView = itemView.news_title
        val content: TextView = itemView.content
        val date: TextView = itemView.date
    }

    var filteredEvents = events.filter { event -> DiffUtils.filterNews(event) }.toMutableList().ifEmpty { Datas.getInstance().events }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = filteredEvents.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(filteredEvents[position].avatar).into(holder.image)
        holder.title.text = filteredEvents[position].title
        holder.content.text = filteredEvents[position].shortDescription
        if (DiffUtils.checkOfRelevance(filteredEvents[position].dateEnd)) {
            holder.date.text =
                "Осталось ${DiffUtils.remainingRelevance(filteredEvents[position].dateEnd)} дней " +
                        "(${filteredEvents[position].dateStart.format(
                            DateTimeFormatter.ofPattern(
                                "dd.MM"
                            )
                        )} - " +
                        "${filteredEvents[position].dateEnd.format(DateTimeFormatter.ofPattern("dd.MM"))})"
        } else {
            holder.date.text =
                "${Datas.months[filteredEvents[position].dateEnd.monthValue - 1]} " +
                        filteredEvents[position].dateEnd.format(DateTimeFormatter.ofPattern("dd, yyyy"))
        }
    }
}