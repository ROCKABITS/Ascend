package com.rockabits.ascend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display

/** Botão redondo com ícone (voltar, etc.). */
@Composable
fun RoundIconButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = Color.White.copy(alpha = 0.6f),
    tint: Color = Color.Black,
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = description, tint = tint, modifier = Modifier.size(26.dp))
    }
}

/** Etiqueta "150 XP". */
@Composable
fun XpPill(xp: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(Icons.Rounded.Star, contentDescription = null, tint = Color(0xFF4FC3F7), modifier = Modifier.size(14.dp))
        Text("$xp XP", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Display)
    }
}

/** Barra de progresso fina (0f..1f). */
@Composable
fun ThinProgressBar(
    progress: Float,
    fill: Color,
    modifier: Modifier = Modifier,
    track: Color = AscendColors.Track,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(track),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(4.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(fill),
        )
    }
}
