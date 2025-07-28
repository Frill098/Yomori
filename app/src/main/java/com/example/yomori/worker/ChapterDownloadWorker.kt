package com.example.yomori.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.yomori.data.remote.NovelsFireSource
import java.io.File

class ChapterDownloadWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val chapterUrl = inputData.getString("chapterUrl") ?: return Result.failure()
        val novelId = inputData.getString("novelId") ?: return Result.failure()
        val chapterId = inputData.getString("chapterId") ?: return Result.failure()

        return try {
            // Récupérer le contenu du chapitre (scraping)
            val content = NovelsFireSource.getChapterContentBlocking(chapterUrl)
            // Sauvegarder dans un fichier local
            val dir = File(applicationContext.filesDir, "novels/$novelId")
            dir.mkdirs()
            val file = File(dir, "$chapterId.html")
            file.writeText(content)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
} 