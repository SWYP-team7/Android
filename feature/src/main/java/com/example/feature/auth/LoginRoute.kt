package com.example.feature.auth

import androidx.compose.runtime.Composable
import com.example.feature.auth.ui.LoginScreen

@Composable
fun LoginRoute(
    onKakaoLogin: () -> Unit
) {
    LoginScreen(
        onKakaoLogin = onKakaoLogin
    )
}