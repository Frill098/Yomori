package com.example.yomori.utils

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREFS_NAME = "yomori_prefs"
    private const val KEY_NOTIF_ENABLED = "notif_enabled"
    private const val KEY_NOTIF_FREQ_HOURS = "notif_freq_hours"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isNotifEnabled(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_NOTIF_ENABLED, true)

    fun setNotifEnabled(context: Context, enabled: Boolean) =
        getPrefs(context).edit().putBoolean(KEY_NOTIF_ENABLED, enabled).apply()

    fun getNotifFreqHours(context: Context): Int =
        getPrefs(context).getInt(KEY_NOTIF_FREQ_HOURS, 6)

    fun setNotifFreqHours(context: Context, hours: Int) =
        getPrefs(context).edit().putInt(KEY_NOTIF_FREQ_HOURS, hours).apply()
} 