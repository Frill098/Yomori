package com.example.yomori.ui.downloads

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DownloadsViewModel(app: Application) : AndroidViewModel(app) {
    private val _downloads = MutableStateFlow<List<DownloadItem>>(emptyList())
    val downloads: StateFlow<List<DownloadItem>> = _downloads

    init {
        observeDownloads()
    }

    private fun observeDownloads() {
        val workManager = WorkManager.getInstance(getApplication())
        viewModelScope.launch {
            workManager.getWorkInfosByTagLiveData("chapter_download").observeForever { infos ->
                _downloads.value = infos.map { info ->
                    DownloadItem(
                        workId = info.id.toString(),
                        chapterTitle = info.progress.getString("chapterTitle") ?: "Chapitre",
                        novelTitle = info.progress.getString("novelTitle") ?: "Novel",
                        state = when (info.state) {
                            WorkInfo.State.ENQUEUED -> DownloadState.QUEUED
                            WorkInfo.State.RUNNING -> DownloadState.RUNNING
                            WorkInfo.State.SUCCEEDED -> DownloadState.SUCCEEDED
                            WorkInfo.State.FAILED -> DownloadState.FAILED
                            else -> DownloadState.QUEUED
                        },
                        progress = info.progress.getInt("progress", if (info.state == WorkInfo.State.SUCCEEDED) 100 else 0)
                    )
                }
            }
        }
    }

    fun retryDownload(workId: String) {
        // Relancer le téléchargement (à implémenter selon ta logique)
    }

    fun deleteDownload(workId: String) {
        WorkManager.getInstance(getApplication()).cancelWorkById(java.util.UUID.fromString(workId))
    }
} 