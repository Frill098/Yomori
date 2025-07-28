package com.example.yomori.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object TranslationService {
    class TranslationException(message: String) : Exception(message)

    suspend fun translate(text: String, source: String, target: String): String = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://libretranslate.de/translate")
            val postData = "q=${text}&source=$source&target=$target&format=text"
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.doOutput = true
            conn.outputStream.write(postData.toByteArray())
            val code = conn.responseCode
            val response = if (code in 200..299) {
                conn.inputStream.bufferedReader().readText()
            } else {
                conn.errorStream?.bufferedReader()?.readText() ?: ""
            }
            if (code !in 200..299) {
                throw TranslationException("API error: $code\n$response")
            }
            val json = JSONObject(response)
            if (!json.has("translatedText")) {
                throw TranslationException("Unexpected API response: $response")
            }
            json.getString("translatedText")
        } catch (e: Exception) {
            throw TranslationException("Translation failed: ${e.message}")
        }
    }
} 