package com.example.yomori.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val novelId: String,
    val chapterId: String,
    val chapterTitle: String
) 