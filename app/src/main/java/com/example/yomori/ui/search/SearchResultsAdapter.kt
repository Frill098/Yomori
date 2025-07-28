package com.example.yomori.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.yomori.R
import com.example.yomori.data.remote.NovelsFireSource

class SearchResultsAdapter :
    ListAdapter<NovelsFireSource.NovelResult, SearchResultsAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.novel_title)
        val cover: ImageView = view.findViewById(R.id.novel_cover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_novel_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val novel = getItem(position)
        holder.title.text = novel.title
        holder.cover.load(novel.coverUrl)
    }

    class DiffCallback : DiffUtil.ItemCallback<NovelsFireSource.NovelResult>() {
        override fun areItemsTheSame(oldItem: NovelsFireSource.NovelResult, newItem: NovelsFireSource.NovelResult) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NovelsFireSource.NovelResult, newItem: NovelsFireSource.NovelResult) =
            oldItem == newItem
    }
} 