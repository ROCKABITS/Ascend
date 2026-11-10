package com.rockabits.ascend.data

import android.content.Context

enum class ThemeMode { LIGHT, DARK, SYSTEM }

/** Guarda XP, quests concluídas e tema no próprio telemóvel (SharedPreferences). */
class ProgressStore(context: Context) {
    private val prefs = context.getSharedPreferences("ascend_prefs", Context.MODE_PRIVATE)

    val xp: Int
        get() = prefs.getInt(KEY_XP, 0)

    val completedIds: Set<String>
        get() = prefs.getStringSet(KEY_COMPLETED, emptySet())?.toSet() ?: emptySet()

    var themeMode: ThemeMode
        get() = runCatching { ThemeMode.valueOf(prefs.getString(KEY_THEME, null) ?: "SYSTEM") }
            .getOrDefault(ThemeMode.SYSTEM)
        set(value) { prefs.edit().putString(KEY_THEME, value.name).apply() }

    fun saveProgress(xp: Int, completedIds: Set<String>) {
        prefs.edit()
            .putInt(KEY_XP, xp)
            .putStringSet(KEY_COMPLETED, completedIds)
            .apply()
    }

    private companion object {
        const val KEY_XP = "xp"
        const val KEY_COMPLETED = "completed_ids"
        const val KEY_THEME = "theme_mode"
    }
}
