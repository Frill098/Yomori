package com.example.yomori.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.yomori.R
import com.example.yomori.data.local.AppDatabase
import com.example.yomori.data.remote.NovelsFireSource

class NewChapterCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val followedNovels = db.novelDao().getAllNovelsOnce() // suspend, non-Flow
        for (novel in followedNovels) {
            val chapters = NovelsFireSource.getChapters(novel.url)
            val lastKnown = db.bookmarkDao().getBookmark(novel.id)?.chapterId
            val latest = chapters.lastOrNull()?.id
            if (latest != null && latest != lastKnown) {
                sendNotification(novel.title, chapters.lastOrNull()?.title ?: "Nouveau chapitre")
            }
        }
        return Result.success()
    }

    private fun sendNotification(novelTitle: String, chapterTitle: String) {
        val channelId = "new_chapter_channel"
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Nouveaux chapitres", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Nouveau chapitre pour $novelTitle")
            .setContentText(chapterTitle)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            // .setContentIntent(pendingIntent) // Pour ouvrir l’app sur le chapitre
            .build()
        manager.notify(novelTitle.hashCode(), notification)
    }
} 