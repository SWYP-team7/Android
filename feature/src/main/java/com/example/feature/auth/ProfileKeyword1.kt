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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileKeyword1 : ComponentActivity() {
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
                containerColor = Color.White,
                bottomBar = { nextButton(checkedStates) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    TopText()
                    ProgressScreen()
                    InfoText()
                    CustomList(checkedStates)
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
                fontWeight = FontWeight.Bold,
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
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "성향 & 성격 표현",
                modifier = Modifier.padding(bottom = 15.dp),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }

    @Composable
    fun nextButton(checkedStates: List<Boolean>) {
        val context = LocalContext.current
        val name = intent.getStringExtra("name") ?: ""
        val birth = intent.getStringExtra("birth") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""
        val profileImageUri = intent.getStringExtra("profileImageUri")
        val items = listOf(
            "내향적 이에요.", "외향적 이에요.", "신중해요.", "충동적 이에요.", "차분해요.",
            "활발해요.", "논리적 이에요.", "감성적 이에요.", "모험적 이에요.", "안정적 이에요."
        )

        Button(
            onClick = {
                if (checkedStates.none { it }) {
                    Toast.makeText(context, "하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    // 선택된 키워드만 추출
                    val selectedKeywords = items.filterIndexed { index, _ -> checkedStates[index] }
                    // SharedPreferences에 선택된 키워드만 저장
                    saveSelectedKeywords(context, selectedKeywords)
                    // Intent로 데이터 전달
                    val intent = Intent(context, ProfileKeyword2::class.java).apply {
                        putExtra("name", name)
                        putExtra("birth", birth)
                        putExtra("gender", gender)
                        putExtra("profileImageUri", profileImageUri)
                    }
                    context.startActivity(intent)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A71FF),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text("다음", fontSize = 15.sp)
        }
    }

    private fun saveSelectedKeywords(context: Context, selectedKeywords: List<String>) {
        // 함수 정의: Context와 선택된 키워드 리스트(List<String>)를 매개변수로 받아 SharedPreferences에 저장
        val sharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        // SharedPreferences 객체를 가져옴. "ProfilePrefs"는 저장소 이름, Context.MODE_PRIVATE는 앱 내에서만 접근 가능
        val gson = Gson()
        // Gson 객체를 생성하여 JSON 직렬화/역직렬화를 처리
        val keywordsJson = gson.toJson(selectedKeywords)
        // selectedKeywords 리스트를 JSON 문자열로 변환 (예: ["내향적 이에요", "차분해요"] -> "[\"내향적 이에요\",\"차분해요\"]")
        with(sharedPreferences.edit()) {
            // SharedPreferences의 편집기를 가져와 데이터를 수정할 준비
            putString("selectedKeywords1", keywordsJson)
            // "selectedKeywords" 키로 JSON 문자열을 SharedPreferences에 저장
            apply()
            // 변경 사항을 비동기적으로 저장소에 반영
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
                if (i < totalSteps) {
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        }
    }

    @Composable
    fun ProgressScreen() {
        StepProgressIndicator(currentStep = 2, totalSteps = 4)
    }

    @Composable
    fun CustomList(checkedStates: MutableList<Boolean>) {
        val items = listOf(
            "내향적 이에요.", "외향적 이에요.", "신중해요.", "충동적 이에요.", "차분해요.",
            "활발해요.", "논리적 이에요.", "감성적 이에요.", "모험적 이에요.", "안정적 이에요."
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                        .padding(vertical = 8.dp)
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (checkedStates[index]) Color(0xFF6A71FF) else Color(0xFF888888)
                        )
                    }
                }
            }
        }
    }
}