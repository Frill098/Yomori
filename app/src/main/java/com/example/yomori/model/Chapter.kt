package com.example.yomori.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey val id: String,
    val novelId: String,
    val title: String,
    val content: String?,
    val number: Int
) 