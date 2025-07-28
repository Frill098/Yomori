package com.example.yomori.data.local

import androidx.room.*
import com.example.yomori.model.DownloadedChapter

@Dao
interface DownloadedChapterDao {
    @Query("SELECT * FROM downloaded_chapters WHERE novelId = :novelId")
    suspend fun getDownloadedChapters(novelId: String): List<DownloadedChapter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedChapter(chapter: DownloadedChapter)

    @Delete
    suspend fun deleteDownloadedChapter(chapter: DownloadedChapter)
} 