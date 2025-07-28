package com.example.yomori.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class Translation(
    @PrimaryKey val key: String, // novelId_chapterId_lang
    val novelId: String,
    val chapterId: String,
    val lang: String,
    val translatedHtml: String
) 