package com.example.swyp

import android.content.Intent
import android.graphics.Color.parseColor
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
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image

class SignUpFinish : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwypTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.sign_up_fininsh_img), // Replace with your image resource
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Adjust scaling as needed (Crop, Fit, etc.)
                        modifier = Modifier.fillMaxSize()
                    )

                    // Scaffold with transparent background
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent // Set to transparent to show the image
                    ) { innerPadding ->
                        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                        ) {
                            Spacer(modifier = Modifier.height(screenHeight * 0.1f))
                           signUpTexts()
                            startButton()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun signUpTexts() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // 위에서부터 차례로 배치
    ) {
        Text(
            text = "님",
            modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
            style = TextStyle(color = Color(parseColor("#6A71FF"))),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            text = "가입을 축하합니다!",
            style = TextStyle(color = Color.Black),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            text = "이제 [시작하기] 버튼을 눌러,",
            modifier = Modifier
                .padding(top = 15.dp),
            style = TextStyle(
                color = Color.Gray
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )

        Text(
            text = "대화를 이어갈 질문 카드를 만나보세요!",
            modifier = Modifier
                .padding(top = 10.dp),
            style = TextStyle(
                color = Color.Gray
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun startButton() {
    val context = LocalContext.current
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
            ,
            onClick = {
//                    val intent = Intent(context, ProfileKeyword2::class.java)
//                    context.startActivity(intent)
            }, // 버튼 클릭 시 동작 (여기서는 비어 있음)

            // 버튼 색상 지정
            colors = ButtonDefaults.buttonColors( // Material3용 ButtonDefaults import 필요
                containerColor = Color(parseColor("#6A71FF")), // HEX → Color 변환
                contentColor = Color.White          // 버튼 안의 텍스트/아이콘 색
            ),

            shape = MaterialTheme.shapes.extraLarge // 버튼 모서리 둥글기 (MaterialTheme 기본 medium 값)
        ) {
            // 버튼 내부 내용 구성
            Text(
                "시작하기", fontSize = 15.sp
            ) // 글자 크기 20sp로 설정) // 버튼 안에 표시할 글자

        }
    }
}





