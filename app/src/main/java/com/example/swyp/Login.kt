package com.example.swyp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swyp.ui.theme.SwypTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwypTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        Spacer(modifier = Modifier.height(screenHeight * 0.1f)) // 화면 높이의 40%
                        SubjectTexts()
                        KakaoButton()
                    }
                }

            }
        }
    }
}

@Composable
fun SubjectTexts() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // 위에서부터 차례로 배치
    ) {
        Text(
            text = "소중한 사람과 함께",
            modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            text = "깊은 대화를 나눠보세요",
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            text = "3초 가입으로 바로 시작해보세요.",
            modifier = Modifier
                .padding(bottom = 15.dp),
            style = TextStyle(
                color = Color.Gray
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }

}

@Composable
fun KakaoButton() {
    Box(
        modifier = Modifier
            .fillMaxSize() //박스가 화면 전체를 씌워짐
            .padding(16.dp) //박스 내부에 16dp 만큼 여백을 줌
    ) {
        Button(
            // 버튼이 차지할 레이아웃 속성 지정
            modifier = Modifier
                .fillMaxWidth() // 가로 전체 너비 사용
                .align(Alignment.BottomCenter) // Box 안에서 하단 중앙에 배치
                .padding(bottom = 50.dp) // 하단에서 80dp 위로 띄움
                .navigationBarsPadding(), // 소프트 내비게이션 바 영역만큼 여백 추가 (버튼이 바에 가리지 않도록)

            onClick = {}, // 버튼 클릭 시 동작 (여기서는 비어 있음)

            // 버튼 색상 지정
            colors = ButtonDefaults.buttonColors( // Material3용 ButtonDefaults import 필요
                containerColor = Color(0xFFFEE500), // 버튼 배경색 (카카오 노란색)
                contentColor = Color.Black          // 버튼 안의 텍스트/아이콘 색
            ),

            shape = MaterialTheme.shapes.medium // 버튼 모서리 둥글기 (MaterialTheme 기본 medium 값)
        ) {
            // 버튼 내부 내용 구성
            Row(
                verticalAlignment = Alignment.CenterVertically,   // 아이콘과 텍스트를 세로 중앙 정렬
                horizontalArrangement = Arrangement.Center        // 가로 방향 중앙 정렬
            ) {
                // 왼쪽 아이콘
                Image(
                    painter = painterResource(id = R.drawable.kakao_login_button), // 리소스 이미지 불러오기 (res/drawable/kakao_login_button.png)
                    contentDescription = "카카오 아이콘", // 접근성(스크린리더) 설명 텍스트
                    modifier = Modifier
                        .size(25.dp)           // 아이콘 크기 20dp
                        .padding(end = 8.dp)   // 아이콘과 텍스트 사이 간격 8dp
                )

                // 텍스트
                Text("카카오로 이용하기"
                ,fontSize = 15.sp) // 글자 크기 20sp로 설정) // 버튼 안에 표시할 글자
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SwypTheme {
        KakaoButton()
        SubjectTexts()
    }
}