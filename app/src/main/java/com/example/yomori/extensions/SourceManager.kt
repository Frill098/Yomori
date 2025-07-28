package com.example.yomori.extensions

import android.content.Context
import com.example.yomori.model.Source

object SourceManager {
    private val sources = mutableListOf<Source>()

    fun getSources(): List<Source> = sources

    fun addSource(source: Source) {
        sources.add(source)
        // Persiste localement si besoin
    }

    fun removeSource(id: String) {
        sources.removeAll { it.id == id }
        // Met à jour le stockage local
    }

    fun updateSources(newSources: List<Source>) {
        sources.clear()
        sources.addAll(newSources)
        // Persiste localement
    }
} 