package com.rockabits.ascend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Assignment
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.LocalIsDark

enum class MainTab(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "Início", Icons.Rounded.Home),
    QUESTS("quests", "Quests", Icons.Rounded.Assignment),
    PROFILE("profile", "Perfil", Icons.Rounded.Person),
    SETTINGS("settings", "Definições", Icons.Rounded.Settings),
}

/** Barra de navegação flutuante, como no Figma. */
@Composable
fun AscendBottomBar(
    selected: MainTab?,
    onSelect: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val barColor = if (LocalIsDark.current) AscendColors.PurpleBar else AscendColors.NavyBar

    Row(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(barColor)
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MainTab.entries.forEach { tab ->
            val isSelected = tab == selected
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable { onSelect(tab) }
                    .semantics { contentDescription = tab.label },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}
