package com.example.yomori.ui.downloads

enum class DownloadState { QUEUED, RUNNING, SUCCEEDED, FAILED }

data class DownloadItem(
    val workId: String,
    val chapterTitle: String,
    val novelTitle: String,
    val state: DownloadState,
    val progress: Int // 0-100
) 