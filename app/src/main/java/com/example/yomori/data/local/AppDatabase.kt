package com.example.yomori.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yomori.model.Novel
import com.example.yomori.model.Chapter
import com.example.yomori.model.Bookmark

@Database(entities = [Novel::class, Chapter::class, Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao
    abstract fun chapterDao(): ChapterDao
    abstract fun bookmarkDao(): BookmarkDao
} 