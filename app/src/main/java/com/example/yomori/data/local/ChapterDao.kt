package com.example.yomori.data.local

import androidx.room.*
import com.example.yomori.model.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE novelId = :novelId ORDER BY number ASC")
    fun getChaptersForNovel(novelId: String): Flow<List<Chapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: Chapter)

    @Delete
    suspend fun deleteChapter(chapter: Chapter)
} 