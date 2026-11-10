package com.rockabits.ascend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.data.Quest
import com.rockabits.ascend.ui.UiState
import com.rockabits.ascend.ui.components.XpPill
import com.rockabits.ascend.ui.components.color
import com.rockabits.ascend.ui.components.icon
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display
import com.rockabits.ascend.ui.theme.LocalIsDark

private enum class QuestFilter(val label: String) {
    ALL("Todas"), TODO("Por fazer"), DONE("Concluídas")
}

@Composable
fun QuestsScreen(
    state: UiState,
    onOpenQuest: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(QuestFilter.ALL) }

    val visible = state.quests.filter { quest ->
        val matchesText = quest.title.contains(query, ignoreCase = true) ||
            quest.description.contains(query, ignoreCase = true)
        val matchesFilter = when (filter) {
            QuestFilter.ALL -> true
            QuestFilter.TODO -> !state.isCompleted(quest.id)
            QuestFilter.DONE -> state.isCompleted(quest.id)
        }
        matchesText && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Quests", fontFamily = Display, fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
        Text(
            "Um novo desafio à tua espera.",
            fontSize = 12.sp,
            color = AscendColors.Muted,
        )
        Spacer(Modifier.height(14.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Pesquisar uma aventura...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f),
                focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f),
            ),
        )
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            QuestFilter.entries.forEach { f ->
                FilterChipPill(f.label, selected = f == filter) { filter = f }
            }
        }
        Spacer(Modifier.height(14.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(visible, key = { it.id }) { quest ->
                QuestListCard(quest, state.isCompleted(quest.id)) { onOpenQuest(quest.id) }
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun FilterChipPill(label: String, selected: Boolean, onClick: () -> Unit) {
    val dark = LocalIsDark.current
    val bg = if (selected) (if (dark) Color.White else Color.Black) else Color.Transparent
    val fg = if (selected) (if (dark) Color.Black else Color.White) else AscendColors.Muted
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp),
    ) {
        Text(label, fontSize = 12.sp, color = fg)
    }
}

@Composable
private fun QuestListCard(quest: Quest, done: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(quest.category.color())
            .clickable(onClick = onClick)
            .alpha(if (done) 0.6f else 1f)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(quest.category.icon(), contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
        Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(quest.title, fontFamily = Display, fontSize = 16.sp, color = Color.Black)
            Text(quest.description, fontSize = 12.sp, color = Color.Black, maxLines = 2)
        }
        if (done) {
            Icon(Icons.Rounded.CheckCircle, contentDescription = "Concluída", tint = Color.Black)
        } else {
            XpPill(quest.xp)
        }
    }
}
