package com.example.yomori.data.local

import androidx.room.*
import com.example.yomori.model.Translation

@Dao
interface TranslationDao {
    @Query("SELECT * FROM translations WHERE key = :key LIMIT 1")
    suspend fun getTranslation(key: String): Translation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: Translation)
} 