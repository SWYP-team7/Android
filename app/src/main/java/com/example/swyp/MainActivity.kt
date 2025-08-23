package com.example.swyp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.swyp.ui.theme.SwypTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint // Hilt 안 쓰면 제거
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SwypTheme { AppRoot() } }

    }
}