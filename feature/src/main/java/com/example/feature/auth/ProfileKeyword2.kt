package com.example.feature.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson

class ProfileKeyword2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

                val checkedStates = remember {
                    mutableStateListOf(
                        false, false, false, false, false,
                        false, false, false, false, false
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        TopText()
                        ProgressScreen()
                        Text()
                        CustomList(checkedStates)
                        nextButton(checkedStates)
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
                .padding(start = 16.dp, end = 16.dp),
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
                .padding(top = 35.dp)
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
                text = "대화 스타일",
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
    fun nextButton(checkedStates: List<Boolean>) {
        val context = LocalContext.current
        val name = intent.getStringExtra("name")
        val items = listOf(
            "말보다 듣는 걸 좋아해요",
            "말하는 것을 좋아해요",
            "공감을 잘해요",
            "솔직한 대화를 좋아해요",
            "진지하고 차분한 대화를 선호해요",
            "농담과 유머가 많아요",
            "질문을 잘해요"
        )
        Box(
            modifier = Modifier
                .fillMaxSize() //박스가 화면 전체를 씌워짐
                .padding(16.dp) //박스 내부에 16dp 만큼 여백을 줌
        ) {
            Button(
                // 버튼이 차지할 레이아웃 속성 지정

                onClick = {
                    if (checkedStates.none { it }) {
                        Toast.makeText(context, "하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 선택된 키워드만 추출
                        val selectedKeywords = items.filterIndexed { index, _ -> checkedStates[index] }
                        // SharedPreferences에 선택된 키워드만 저장
                        saveSelectedKeywords(context, selectedKeywords)

                        val intent = Intent(context, ProfileKeyword3::class.java)
                            .apply {
                                putExtra("name", name)
                            }
                        context.startActivity(intent)
                    }
                }, // 버튼 클릭 시 동작 (여기서는 비어 있음)

                modifier = Modifier
                    .fillMaxWidth() // 가로 전체 너비 사용
                    .align(Alignment.BottomCenter) // Box 안에서 하단 중앙에 배치
                    .padding(bottom = 40.dp) // 하단에서 80dp 위로 띄움
                ,

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
                ) // 글자 크기 20sp로 설정) // 버튼 안에 표시할 글자

            }
        }
    }

    private fun saveSelectedKeywords(context: Context, selectedKeywords: List<String>) {
        val sharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val keywordsJson = gson.toJson(selectedKeywords)
        with(sharedPreferences.edit()) {
            putString("selectedKeywords2", keywordsJson)
            apply()
        }
    }

    @Composable
    fun StepProgressIndicator(currentStep: Int, totalSteps: Int) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // 수평 가운데 정렬
        ) {
            for (i in 1..totalSteps) {
                Box(
                    modifier = Modifier
                        .size(width = 55.dp, height = 6.dp)
                        .background(
                            color = if (i <= currentStep) Color(0xFFFF6A71FF) else Color.LightGray,
                            shape = RoundedCornerShape(2.dp)
                        )

                )

                if (i < totalSteps) {
                    Spacer(modifier = Modifier.width(3.dp)) // Adjust space size as needed
                }
            }
        }
    }

    @Composable
    fun ProgressScreen() {
        StepProgressIndicator(currentStep = 3, totalSteps = 4)
    }

    @Composable
    fun CustomList(checkedStates: MutableList<Boolean>) {
        // 각 항목의 선택 상태를 동적으로 관리
        val items = listOf(
            "말보다 듣는 걸 좋아해요",
            "말하는 것을 좋아해요",
            "공감을 잘해요",
            "솔직한 대화를 좋아해요",
            "진지하고 차분한 대화를 선호해요",
            "농담과 유머가 많아요",
            "질문을 잘해요"
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                        .padding(vertical = 6.dp)
                        .background(
                            color = if (checkedStates[index]) Color(0xFFE9EAFF) else Color(
                                0xFFF6F6F6
                            ),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            checkedStates[index] = !checkedStates[index] // 선택 상태 토글
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 커스텀 둥근 체크박스
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = if (checkedStates[index]) Color(0xFF6A71FF) else Color(
                                        0xFFE0E0E0
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (checkedStates[index]) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        if (checkedStates[index]) {
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF6A71FF)
                            )
                        } else {
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF888888)
                            )
                        }
                    }
                }
            }
        }
    }




}



@Composable
fun GreetingPreview4() {

}