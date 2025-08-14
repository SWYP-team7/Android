package com.example.swyp

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

class ProfileKeyword2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwypTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        TopText()
                        Text()
                        nextButton()
                    }
                }
            }
        }
    }
}

@Composable
fun TopText() {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center // 기본 정렬을 중앙으로
    ) {
        // 중앙 텍스트
        Text(
            text = "기본 정보 입력",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )

        // 왼쪽 백 버튼
        IconButton(
            onClick = { backDispatcher?.onBackPressed() },
            modifier = Modifier.align(Alignment.CenterStart) // 왼쪽에 고정
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "뒤로가기",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun Text() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center, // 세로 중앙 정렬
        horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬
    ) {
        Text(
            text = "나를 표현하는 키워드를 골라주세요.",
            modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "성향 & 성격 표현",
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
fun nextButton() {
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
                .padding(bottom = 40.dp) // 하단에서 80dp 위로 띄움
                .navigationBarsPadding(), // 소프트 내비게이션 바 영역만큼 여백 추가 (버튼이 바에 가리지 않도록)

            onClick = {}, // 버튼 클릭 시 동작 (여기서는 비어 있음)

            // 버튼 색상 지정
            colors = ButtonDefaults.buttonColors( // Material3용 ButtonDefaults import 필요
                containerColor = Color(parseColor("#6A71FF")), // HEX → Color 변환
                contentColor = Color.White          // 버튼 안의 텍스트/아이콘 색
            ),

            shape = MaterialTheme.shapes.extraLarge // 버튼 모서리 둥글기 (MaterialTheme 기본 medium 값)
        ) {
            // 버튼 내부 내용 구성
            Text(
                "다음", fontSize = 15.sp
            ) // 글자 크기 20sp로 설정)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    SwypTheme {
    }
}