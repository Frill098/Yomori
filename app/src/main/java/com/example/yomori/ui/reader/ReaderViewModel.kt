package com.example.yomori.ui.reader

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.yomori.data.remote.NovelsFireSource
import com.example.yomori.model.Bookmark
import com.example.yomori.data.local.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReaderViewModel(
    app: Application,
    private val bookmarkDao: BookmarkDao
) : AndroidViewModel(app) {
    private val _theme = MutableStateFlow(ReaderTheme.LIGHT)
    val theme: StateFlow<ReaderTheme> = _theme

    private val _fontSize = MutableStateFlow(18)
    val fontSize: StateFlow<Int> = _fontSize

    fun setTheme(theme: ReaderTheme) { _theme.value = theme }
    fun increaseFont() { _fontSize.value = (_fontSize.value + 2).coerceAtMost(32) }
    fun decreaseFont() { _fontSize.value = (_fontSize.value - 2).coerceAtLeast(12) }

    fun setBookmark(novelId: String, chapterId: String, chapterTitle: String) {
        viewModelScope.launch {
            bookmarkDao.setBookmark(Bookmark(novelId, chapterId, chapterTitle))
        }
    }
    suspend fun getBookmark(novelId: String): Bookmark? = bookmarkDao.getBookmark(novelId)

    suspend fun getChapterContent(chapterUrl: String): String {
        return NovelsFireSource.getChapterContent(chapterUrl)
    }
} 