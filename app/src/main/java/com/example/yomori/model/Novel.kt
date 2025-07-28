package com.example.yomori.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novels")
data class Novel(
    @PrimaryKey val id: String,
    val title: String,
    val author: String?,
    val coverUrl: String?,
    val description: String?,
    val source: String
) 