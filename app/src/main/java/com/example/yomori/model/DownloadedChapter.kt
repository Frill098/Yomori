package com.example.yomori.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_chapters")
data class DownloadedChapter(
    @PrimaryKey val id: String, // chapterId
    val novelId: String,
    val title: String,
    val filePath: String
) 