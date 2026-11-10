package com.rockabits.ascend.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rockabits.ascend.data.ProgressStore
import com.rockabits.ascend.data.Quest
import com.rockabits.ascend.data.QuestRepository
import com.rockabits.ascend.data.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

const val XP_PER_LEVEL = 1000

data class UiState(
    val quests: List<Quest>,
    val xp: Int,
    val completedIds: Set<String>,
    val themeMode: ThemeMode,
) {
    val level: Int get() = xp / XP_PER_LEVEL + 1
    val xpInLevel: Int get() = xp % XP_PER_LEVEL
    val completedCount: Int get() = completedIds.size
    val levelTitle: String
        get() = when {
            level <= 2 -> "Principiante"
            level <= 5 -> "Explorador"
            else -> "Veterano"
        }

    fun questById(id: String): Quest? = quests.firstOrNull { it.id == id }
    fun isCompleted(id: String): Boolean = id in completedIds
}

class AscendViewModel(app: Application) : AndroidViewModel(app) {
    private val store = ProgressStore(app)

    private val _state = MutableStateFlow(
        UiState(
            quests = QuestRepository.quests,
            xp = store.xp,
            completedIds = store.completedIds,
            themeMode = store.themeMode,
        )
    )
    val state: StateFlow<UiState> = _state.asStateFlow()

    /** Chamado quando o utilizador carrega em "Resgatar XP" depois de enviar a prova. */
    fun completeQuest(id: String) {
        val current = _state.value
        val quest = current.questById(id) ?: return
        if (current.isCompleted(id)) return

        val newIds = current.completedIds + id
        val newXp = current.xp + quest.xp
        store.saveProgress(newXp, newIds)
        _state.value = current.copy(xp = newXp, completedIds = newIds)
    }

    fun setThemeMode(mode: ThemeMode) {
        store.themeMode = mode
        _state.value = _state.value.copy(themeMode = mode)
    }
}
