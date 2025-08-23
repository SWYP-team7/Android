// app/src/main/java/com/example/swyp/feature/auth/ui/LoginScreen.kt
package com.example.feature.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.R
@Composable
fun LoginScreen(
    onKakaoLogin: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.kakao_login_button),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 투명 Scaffold
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { inner ->
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            Column(
                modifier = Modifier.padding(inner)
            ) {
                Spacer(Modifier.height(screenHeight * 0.1f))
                SubjectTexts()
                KakaoButton(onClick = onKakaoLogin)
            }
        }
    }
}

@Composable
private fun SubjectTexts() {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = "소중한 사람과 함께",
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "깊은 대화를 나눠보세요",
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = "3초 가입으로 바로 시작해보세요.",
            style = TextStyle(color = Color.Gray),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun KakaoButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .navigationBarsPadding(),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFEE500),
                contentColor = Color.Black
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kakao_login_button),
                    contentDescription = "카카오 아이콘",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 8.dp)
                )
                Text("카카오로 이용하기", fontSize = 15.sp)
            }
        }
    }
}

