package com.example.yomori.ui.downloads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yomori.R

class DownloadsAdapter(
    val onRetry: (DownloadItem) -> Unit,
    val onDelete: (DownloadItem) -> Unit
) : ListAdapter<DownloadItem, DownloadsAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chapterTitle: TextView = view.findViewById(R.id.chapter_title)
        val novelTitle: TextView = view.findViewById(R.id.novel_title)
        val progressBar: ProgressBar = view.findViewById(R.id.download_progress)
        val stateText: TextView = view.findViewById(R.id.download_state)
        val retryButton: ImageButton = view.findViewById(R.id.retry_button)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_download, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.chapterTitle.text = item.chapterTitle
        holder.novelTitle.text = item.novelTitle
        holder.progressBar.progress = item.progress
        holder.stateText.text = when (item.state) {
            DownloadState.QUEUED -> "En attente"
            DownloadState.RUNNING -> "En cours"
            DownloadState.SUCCEEDED -> "Terminé"
            DownloadState.FAILED -> "Échoué"
        }
        holder.retryButton.visibility = if (item.state == DownloadState.FAILED) View.VISIBLE else View.GONE
        holder.retryButton.setOnClickListener { onRetry(item) }
        holder.deleteButton.setOnClickListener { onDelete(item) }
    }

    class DiffCallback : DiffUtil.ItemCallback<DownloadItem>() {
        override fun areItemsTheSame(oldItem: DownloadItem, newItem: DownloadItem) = oldItem.workId == newItem.workId
        override fun areContentsTheSame(oldItem: DownloadItem, newItem: DownloadItem) = oldItem == newItem
    }
} 