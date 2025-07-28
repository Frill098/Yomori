package com.example.yomori.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomori.data.remote.NovelsFireSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _results = MutableStateFlow<List<NovelsFireSource.NovelResult>>(emptyList())
    val results: StateFlow<List<NovelsFireSource.NovelResult>> = _results

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun search(query: String) {
        viewModelScope.launch {
            _loading.value = true
            _results.value = try {
                NovelsFireSource.searchNovels(query)
            } catch (e: Exception) {
                emptyList()
            }
            _loading.value = false
        }
    }
} 