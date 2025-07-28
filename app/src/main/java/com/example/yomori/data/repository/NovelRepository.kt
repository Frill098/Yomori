package com.example.yomori.data.repository

import com.example.yomori.data.local.NovelDao
import com.example.yomori.model.Novel
import kotlinx.coroutines.flow.Flow
 
class NovelRepository(private val novelDao: NovelDao) {
    fun getAllNovels(): Flow<List<Novel>> = novelDao.getAllNovels()
    suspend fun insertNovel(novel: Novel) = novelDao.insertNovel(novel)
    suspend fun deleteNovel(novel: Novel) = novelDao.deleteNovel(novel)
} 