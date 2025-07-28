package com.example.yomori.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

object NovelsFireSource {
    data class NovelResult(
        val id: String,
        val title: String,
        val url: String,
        val coverUrl: String?
    )

    suspend fun searchNovels(query: String): List<NovelResult> = withContext(Dispatchers.IO) {
        val url = "https://novelfire.net/search?search=${query.replace(" ", "+")}"
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0") // Pour éviter certains blocages
            .get()

        val results = mutableListOf<NovelResult>()
        val items = doc.select("div.novel-item")
        for (item in items) {
            val titleElement = item.selectFirst("a.novel-title")
            val title = titleElement?.text() ?: continue
            val novelUrl = "https://novelfire.net" + (titleElement.attr("href") ?: "")
            val id = novelUrl.substringAfterLast("/book/").takeWhile { it != '/' }
            val coverUrl = item.selectFirst("img.novel-cover")?.attr("src")
            results.add(NovelResult(id, title, novelUrl, coverUrl))
        }
        results
    }

    data class ChapterResult(
        val id: String,
        val title: String,
        val url: String,
        val number: Int
    )

    suspend fun getChapters(novelUrl: String): List<ChapterResult> = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(novelUrl)
            .userAgent("Mozilla/5.0")
            .get()
        val chapters = mutableListOf<ChapterResult>()
        val items = doc.select("ul.chapter-list li a")
        var number = 1
        for (item in items) {
            val title = item.text()
            val url = "https://novelfire.net" + item.attr("href")
            val id = url.substringAfterLast("/")
            chapters.add(ChapterResult(id, title, url, number++))
        }
        chapters
    }

    suspend fun getChapterContent(chapterUrl: String): String = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(chapterUrl)
            .userAgent("Mozilla/5.0")
            .get()
        val content = doc.selectFirst("div.chapter-content")?.html() ?: ""
        content
    }
} 