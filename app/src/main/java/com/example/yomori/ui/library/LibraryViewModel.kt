package com.example.yomori.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomori.data.repository.NovelRepository
import com.example.yomori.model.Novel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: NovelRepository) : ViewModel() {
    val novels: StateFlow<List<Novel>> = repository.getAllNovels()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addNovel(novel: Novel) {
        viewModelScope.launch { repository.insertNovel(novel) }
    }

    fun removeNovel(novel: Novel) {
        viewModelScope.launch { repository.deleteNovel(novel) }
    }
} 