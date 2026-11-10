package com.rockabits.ascend.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rockabits.ascend.data.Quest
import com.rockabits.ascend.ui.components.RoundIconButton
import com.rockabits.ascend.ui.components.XpPill
import com.rockabits.ascend.ui.components.color
import com.rockabits.ascend.ui.theme.AscendColors
import com.rockabits.ascend.ui.theme.Display

/**
 * Detalhe de uma quest.
 * Prova da versão 1: uma foto. Por agora a foto fica só no ecrã (não é enviada nem guardada) —
 * a validação da foto é uma questão em aberto no documento A02.
 */
@Composable
fun QuestDetailScreen(
    quest: Quest?,
    completed: Boolean,
    onBack: () -> Unit,
    onRedeem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (quest == null) {
        Box(modifier.fillMaxSize().statusBarsPadding().padding(20.dp)) {
            RoundIconButton(
                Icons.AutoMirrored.Rounded.ArrowBack, "Voltar", onBack,
                background = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                tint = MaterialTheme.colorScheme.onBackground,
            )
            Text("Quest não encontrada.", modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.onBackground)
        }
        return
    }

    var photo by rememberSaveable(quest.id) { mutableStateOf<Bitmap?>(null) }
    val takePhoto = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) photo = bitmap
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Cabeçalho colorido
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(quest.category.color())
                .statusBarsPadding()
                .height(190.dp)
                .padding(20.dp),
        ) {
            RoundIconButton(Icons.AutoMirrored.Rounded.ArrowBack, "Voltar", onBack, modifier = Modifier.align(Alignment.TopStart))
            Row(
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(quest.title, fontFamily = Display, fontSize = 28.sp, color = Color.Black, modifier = Modifier.weight(1f))
                XpPill(quest.xp)
            }
        }

        // Corpo
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(quest.description, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)

            Text("Prova", fontFamily = Display, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)

            val captured = photo
            if (captured != null) {
                Image(
                    bitmap = captured.asImageBitmap(),
                    contentDescription = "Foto da prova",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(20.dp)),
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Ainda sem foto", fontSize = 13.sp, color = AscendColors.Muted)
                }
            }

            if (!completed) {
                OutlinedButton(
                    onClick = { takePhoto.launch(null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Icon(Icons.Rounded.CameraAlt, contentDescription = null)
                    Text(
                        if (captured == null) "  Tirar foto" else "  Tirar outra foto",
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }

        // Botão principal
        Button(
            onClick = onRedeem,
            enabled = !completed && photo != null,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .height(76.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Text(
                text = when {
                    completed -> "Concluída"
                    photo == null -> "Tira uma foto para resgatar"
                    else -> "Resgatar XP"
                },
                fontFamily = Display,
                fontSize = 20.sp,
            )
        }
    }
}
