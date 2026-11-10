package com.rockabits.ascend.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.data.Quest
import com.rockabits.ascend.data.QuestCategory
import com.rockabits.ascend.data.QuestRepository
import com.rockabits.ascend.ui.UiState
import com.rockabits.ascend.ui.components.ThinProgressBar
import com.rockabits.ascend.ui.components.color
import com.rockabits.ascend.ui.components.icon
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display
import com.rockabits.ascend.ui.theme.LocalIsDark
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

private val CardShape = RoundedCornerShape(25.dp)

@Composable
fun HomeScreen(
    state: UiState,
    onOpenQuest: (String) -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Header(state, onOpenProfile)
        Spacer(Modifier.height(20.dp))

        state.questById(QuestRepository.DAILY_ID)?.let { daily ->
            DailyQuestCard(daily, state.isCompleted(daily.id)) { onOpenQuest(daily.id) }
        }
        Spacer(Modifier.height(20.dp))

        WeekStrip()
        Spacer(Modifier.height(20.dp))

        Text("Quests", fontFamily = Display, fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(12.dp))
        QuestGrid(state, onOpenQuest)
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun Header(state: UiState, onOpenProfile: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(Modifier.size(50.dp)) // espaço onde no Figma está o botão do menu lateral
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Olá, utilizador",
                fontFamily = Display,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                "Lvl ${state.level}: ${state.levelTitle}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(AscendColors.Purple)
                .clickable(onClick = onOpenProfile),
            contentAlignment = Alignment.Center,
        ) {
            Text("U", fontFamily = Display, fontSize = 20.sp, color = Color.Black)
        }
    }
}

@Composable
private fun DailyQuestCard(quest: Quest, done: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(147.dp)
            .clip(CardShape)
            .background(AscendColors.Purple)
            .clickable(onClick = onClick)
            .padding(20.dp),
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text("Quest\nDiária", fontFamily = Display, fontSize = 24.sp, color = Color.Black, lineHeight = 26.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                if (done) "Concluída hoje!" else "Completa o desafio de hoje · +${quest.xp} XP",
                fontSize = 12.sp,
                color = Color.Black,
            )
        }
        if (done) {
            Icon(
                Icons.Rounded.CheckCircle,
                contentDescription = "Concluída",
                tint = Color.Black,
                modifier = Modifier.align(Alignment.TopEnd).size(32.dp),
            )
        }
    }
}

@Composable
private fun WeekStrip() {
    val today = LocalDate.now()
    val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val labels = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom")
    val dark = LocalIsDark.current

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        labels.forEachIndexed { index, label ->
            val date = monday.plusDays(index.toLong())
            val selected = date == today
            val bg = if (selected) (if (dark) Color.White else Color.Black) else Color.Transparent
            val fg = if (selected) (if (dark) Color.Black else Color.White) else MaterialTheme.colorScheme.onBackground
            val sub = if (selected) fg else AscendColors.Muted
            Column(
                modifier = Modifier
                    .width(40.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(bg)
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(50.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(label, fontSize = 10.sp, color = sub)
                Spacer(Modifier.height(4.dp))
                Text(date.dayOfMonth.toString(), fontSize = 10.sp, color = fg)
            }
        }
    }
}

@Composable
private fun QuestGrid(state: UiState, onOpenQuest: (String) -> Unit) {
    val photo = state.questById("novo-olhar")
    val explore = state.questById("explora")
    val walk = state.questById("vai-mais-longe")

    Row(modifier = Modifier.fillMaxWidth().height(231.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (photo != null) {
            TallQuestCard(
                quest = photo,
                done = state.isCompleted(photo.id),
                modifier = Modifier.weight(1f).fillMaxSize(),
                onClick = { onOpenQuest(photo.id) },
            )
        }
        Column(modifier = Modifier.weight(1f).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (explore != null) {
                SmallQuestCard(
                    quest = explore,
                    done = state.isCompleted(explore.id),
                    modifier = Modifier.weight(131f).fillMaxWidth(),
                    onClick = { onOpenQuest(explore.id) },
                )
            }
            CompleteThreeCard(
                done = state.quests.count { it.category != QuestCategory.DAILY && state.isCompleted(it.id) },
                modifier = Modifier.weight(92f).fillMaxWidth(),
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    if (walk != null) {
        WideQuestCard(walk, state.isCompleted(walk.id)) { onOpenQuest(walk.id) }
    }
}

@Composable
private fun TallQuestCard(quest: Quest, done: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(CardShape)
            .background(quest.category.color())
            .clickable(onClick = onClick)
            .alpha(if (done) 0.6f else 1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        IconTile(quest)
        Spacer(Modifier.height(14.dp))
        Text(quest.title, fontFamily = Display, fontSize = 16.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(Modifier.height(6.dp))
        Text(quest.description, fontSize = 12.sp, color = Color.Black, textAlign = TextAlign.Center)
        if (done) {
            Spacer(Modifier.height(10.dp))
            Text("Concluída", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
private fun SmallQuestCard(quest: Quest, done: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(CardShape)
            .background(quest.category.color())
            .clickable(onClick = onClick)
            .alpha(if (done) 0.6f else 1f)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(quest.title, fontFamily = Display, fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(Modifier.height(4.dp))
        Text(
            if (done) "Concluída" else "Visita um parque ou local onde nunca estiveste",
            fontSize = 10.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CompleteThreeCard(done: Int, modifier: Modifier) {
    val shown = done.coerceAtMost(3)
    Column(
        modifier = modifier
            .clip(CardShape)
            .background(AscendColors.Pink)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Rounded.Layers, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
            Text("Completa\n3 quests", fontFamily = Display, fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.End, lineHeight = 16.sp)
        }
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            ThinProgressBar(shown / 3f, AscendColors.PinkDark, Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            Text("$shown/3", fontSize = 12.sp, color = Color.Black)
        }
    }
}

@Composable
private fun WideQuestCard(quest: Quest, done: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(81.dp)
            .clip(CardShape)
            .background(quest.category.color())
            .clickable(onClick = onClick)
            .alpha(if (done) 0.6f else 1f)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconTile(quest, size = 46)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(quest.title, fontFamily = Display, fontSize = 16.sp, color = Color.Black)
            Text(if (done) "Concluída" else "Caminha 5 km", fontSize = 13.sp, color = Color.Black)
        }
    }
}

@Composable
private fun IconTile(quest: Quest, size: Int = 46) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(quest.category.icon(), contentDescription = null, tint = Color.Black, modifier = Modifier.size((size * 0.6f).dp))
    }
}
