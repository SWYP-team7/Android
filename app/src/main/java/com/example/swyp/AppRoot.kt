// app/src/main/java/com/example/swyp/AppRoot.kt
package com.example.swyp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.auth.LoginRoute

@Composable
fun AppRoot() {
    val nav = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = nav,
            startDestination = Routes.Login
        ) {
            composable(Routes.Login) {
                LoginRoute(
                    onKakaoLogin = { nav.navigate(Routes.ProfileSelect) }
                )
            }
            composable(Routes.ProfileSelect) {
                // TODO: 실제 ProfileSelectRoute로 대체
                PlaceholderScreen("ProfileSelect")
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    androidx.compose.material3.Text("Here: $name")
}
