package com.rockabits.ascend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rockabits.ascend.ui.AscendApp
import com.rockabits.ascend.ui.AscendViewModel
import com.rockabits.ascend.ui.theme.AscendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AscendViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            AscendTheme(themeMode = state.themeMode) {
                AscendApp(state = state, viewModel = viewModel)
            }
        }
    }
}
