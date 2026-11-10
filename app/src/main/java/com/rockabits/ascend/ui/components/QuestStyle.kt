package com.rockabits.ascend.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rockabits.ascend.data.QuestCategory
import com.rockabits.ascend.ui.theme.AscendColors

fun QuestCategory.color(): Color = when (this) {
    QuestCategory.DAILY -> AscendColors.Purple
    QuestCategory.PHOTO -> AscendColors.Orange
    QuestCategory.EXPLORE -> AscendColors.Blue
    QuestCategory.WALK -> AscendColors.Green
}

fun QuestCategory.icon(): ImageVector = when (this) {
    QuestCategory.DAILY -> Icons.Rounded.PriorityHigh
    QuestCategory.PHOTO -> Icons.Rounded.CameraAlt
    QuestCategory.EXPLORE -> Icons.Rounded.Map
    QuestCategory.WALK -> Icons.Rounded.DirectionsWalk
}
