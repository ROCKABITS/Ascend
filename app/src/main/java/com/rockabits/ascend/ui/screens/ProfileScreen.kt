package com.rockabits.ascend.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.ui.UiState
import com.rockabits.ascend.ui.XP_PER_LEVEL
import com.rockabits.ascend.ui.components.ThinProgressBar
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display

/** Perfil da versão 1: só nome, nível, XP e quests concluídas. */
@Composable
fun ProfileScreen(state: UiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(16.dp))
        Text("O teu perfil", fontFamily = Display, fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(AscendColors.Purple)
                .padding(20.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Utilizador", fontFamily = Display, fontSize = 22.sp, color = Color.Black)
                Text("Nv. ${state.level}", fontFamily = Display, fontSize = 16.sp, color = Color.Black)
            }
            Spacer(Modifier.height(4.dp))
            Text(state.levelTitle, fontSize = 12.sp, color = Color.Black)
            Spacer(Modifier.height(14.dp))
            ThinProgressBar(
                progress = state.xpInLevel / XP_PER_LEVEL.toFloat(),
                fill = Color.Black,
                track = Color.White.copy(alpha = 0.6f),
            )
            Spacer(Modifier.height(6.dp))
            Text("${state.xpInLevel} / $XP_PER_LEVEL XP", fontSize = 12.sp, color = Color.Black)
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Quests feitas", state.completedCount.toString(), AscendColors.Orange, Modifier.weight(1f))
            StatCard("XP total", state.xp.toString(), AscendColors.Blue, Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(16.dp),
    ) {
        Text(value, fontFamily = Display, fontSize = 28.sp, color = Color.Black)
        Text(label, fontSize = 12.sp, color = Color.Black)
    }
}
