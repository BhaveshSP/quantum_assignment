package com.bhaveshsp.quantumassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhaveshsp.quantumassignment.R
import com.bhaveshsp.quantumassignment.models.News
import com.bumptech.glide.Glide

class NewsAdapter(var context :Context ,var newsList : ArrayList<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var publishedAtText : TextView = itemView.findViewById(R.id.newsPublishedAtText)
        var briefText : TextView = itemView.findViewById(R.id.newsBriefText)
        var sourceText : TextView = itemView.findViewById(R.id.newsSourceText)
        var titleText : TextView = itemView.findViewById(R.id.newsTitleText)
        var image : ImageView = itemView.findViewById(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.news_item_view_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.briefText.text = newsItem.description
        holder.publishedAtText.text = "2 hours ago"
        holder.sourceText.text = newsItem.source!!.name
        holder.titleText.text = newsItem.title
        Glide.with(context).load(newsItem.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}