package com.example.yomori.data.local

import androidx.room.*
import com.example.yomori.model.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks WHERE novelId = :novelId LIMIT 1")
    suspend fun getBookmark(novelId: String): Bookmark?

    @Query("DELETE FROM bookmarks WHERE novelId = :novelId")
    suspend fun deleteBookmark(novelId: String)
} 