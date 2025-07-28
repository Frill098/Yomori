package com.example.yomori.data.local

import androidx.room.*
import com.example.yomori.model.Novel
import kotlinx.coroutines.flow.Flow

@Dao
interface NovelDao {
    @Query("SELECT * FROM novels")
    fun getAllNovels(): Flow<List<Novel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNovel(novel: Novel)

    @Delete
    suspend fun deleteNovel(novel: Novel)
} 