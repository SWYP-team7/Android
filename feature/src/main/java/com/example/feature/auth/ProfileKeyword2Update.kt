package com.example.feature.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileKeyword2Update : ComponentActivity() {

    private val items = listOf(
        "말보다 듣는 걸 좋아해요",
        "말하는 것을 좋아해요",
        "공감을 잘해요",
        "솔직한 대화를 좋아해요",
        "진지하고 차분한 대화를 선호해요",
        "농담과 유머가 많아요",
        "질문을 잘해요"
    )

    private lateinit var checkedStates: MutableList<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // SharedPreferences에서 저장된 키워드 불러오기
        val sharedPreferences = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val savedKeywordsJson = sharedPreferences.getString("selectedKeywords2", "[]")
        val savedKeywords: List<String> =
            gson.fromJson(savedKeywordsJson, object : TypeToken<List<String>>() {}.type)

        // 체크 상태 초기화 (savedKeywords에 있으면 true)
        checkedStates = mutableStateListOf<Boolean>().apply {
            items.forEach { add(it in savedKeywords) }
        }

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.White,
                bottomBar = { BottomButtons() }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    TopText()
                    ProgressScreen()
                    InfoText()
                    CustomList()
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
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "기본 정보 입력",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            IconButton(
                onClick = { backDispatcher?.onBackPressed() },
                modifier = Modifier.align(Alignment.CenterStart)
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
    fun InfoText() {
        Column(
            modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "나를 표현하는 키워드를 골라주세요.",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "대화 스타일",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }

    @Composable
    fun BottomButtons() {
        val context = LocalContext.current
        val name = intent.getStringExtra("name") ?: ""

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 이전 버튼
            OutlinedButton(
                onClick = { finish() }, // 이전 액티비티로 돌아가기
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("이전", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 다음 버튼
            Button(
                onClick = {
                    if (checkedStates.none { it }) {
                        Toast.makeText(context, "하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        val selectedKeywords = items.filterIndexed { index, _ -> checkedStates[index] }
                        saveSelectedKeywords(context, selectedKeywords)

                        val intent = Intent(context, ProfileKeyword3::class.java).apply {
                            putExtra("name", name)
                        }
                        startActivity(intent)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A71FF),
                    contentColor = Color.White
                )
            ) {
                Text("다음", fontSize = 15.sp)
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
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..totalSteps) {
                Box(
                    modifier = Modifier
                        .size(width = 55.dp, height = 6.dp)
                        .background(
                            color = if (i <= currentStep) Color(0xFF6A71FF) else Color.LightGray,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                if (i < totalSteps) Spacer(modifier = Modifier.width(3.dp))
            }
        }
    }

    @Composable
    fun ProgressScreen() {
        StepProgressIndicator(currentStep = 2, totalSteps = 3)
    }

    @Composable
    fun CustomList() {
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
                            color = if (checkedStates[index]) Color(0xFFE9EAFF) else Color(0xFFF6F6F6),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable { checkedStates[index] = !checkedStates[index] },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = if (checkedStates[index]) Color(0xFF6A71FF) else Color(0xFFE0E0E0),
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
                        Text(
                            text = text,
                            color = if (checkedStates[index]) Color(0xFF6A71FF) else Color(0xFF888888)
                        )
                    }
                }
            }
        }
    }
}
