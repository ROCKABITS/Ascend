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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.data.ThemeMode
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Definições", fontFamily = Display, fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                .padding(16.dp),
        ) {
            Text("Aspeto", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeOption("Claro", themeMode == ThemeMode.LIGHT, Modifier.weight(1f)) { onThemeChange(ThemeMode.LIGHT) }
                ThemeOption("Escuro", themeMode == ThemeMode.DARK, Modifier.weight(1f)) { onThemeChange(ThemeMode.DARK) }
                ThemeOption("Sistema", themeMode == ThemeMode.SYSTEM, Modifier.weight(1f)) { onThemeChange(ThemeMode.SYSTEM) }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Ascend v0.1.0", fontSize = 12.sp, color = AscendColors.Muted)
    }
}

@Composable
private fun ThemeOption(label: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) AscendColors.Purple else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, fontSize = 12.sp, color = if (selected) Color.Black else MaterialTheme.colorScheme.onBackground)
    }
}
