package com.example.swyp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.swyp.ui.theme.SwypTheme
import kotlinx.coroutines.launch

class ProfileSelect : ComponentActivity() {
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
                        ProgressScreen()
                        Text()
                        ProfileScreen() // 여기서 ProfileScreen() 호출
                        nameTextField()
                        birthTextField()
                        selectGender()
                        nextButton()
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
                text = "나의 프로필을 설정해주세요.",
                modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }

    @Composable
    fun nextButton() {
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
                    val intent = Intent(context, ProfileKeyword2::class.java)
                    context.startActivity(intent)
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
                    "다음", fontSize = 15.sp
                ) // 글자 크기 20sp로 설정) // 버튼 안에 표시할 글자

            }
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
        StepProgressIndicator(currentStep = 1, totalSteps = 5)
    }


    @Composable
    fun nameTextField() {
        // 상태 관리
        val text = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),

            ) {
            Text(
                text = "이름",
                modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }


        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            placeholder = { Text("성함을 입력해주세요.", color = Color.LightGray , fontSize = 14.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp) // 고정된 높이 설정 (예: 56dp, Material Design 표준)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)), // 테두리 유지
            textStyle = TextStyle(textAlign = TextAlign.Start), // 텍스트를 가운데 정렬
            maxLines = 1, // 줄바꿈 방지
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White, // 배경색을 흰색으로 설정
                unfocusedIndicatorColor = Color.Transparent, // 포커스 해제 시 밑줄 투명
                focusedIndicatorColor = Color.Transparent // 포커스 시 밑줄 투명
            )
        )
    }


    @Composable
    fun birthTextField() {
        // 상태 관리
        val text = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),

            ) {
            Text(
                text = "생년월일",
                modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        TextField(
            value = text.value,
            onValueChange = { newText ->
                // 숫자만 허용 (0-9만 필터링)
                val filteredText = newText.filter { it.isDigit() }
                if (filteredText.length <= 8) { // 8자리 제한
                    text.value = filteredText
                } },
            placeholder = { Text("생년월일 8자리 입력해주세요. ex)19990909", color = Color.LightGray , fontSize = 14.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp) // 고정된 높이 설정 (예: 56dp, Material Design 표준)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)), // 테두리 유지
            textStyle = TextStyle(textAlign = TextAlign.Start), // 텍스트를 가운데 정렬
            maxLines = 1, // 줄바꿈 방지
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White, // 배경색을 흰색으로 설정
                unfocusedIndicatorColor = Color.Transparent, // 포커스 해제 시 밑줄 투명
                focusedIndicatorColor = Color.Transparent // 포커스 시 밑줄 투명
            )
        )

    }
    @Composable
    fun selectGender() {
        Row(
            modifier = Modifier
                .padding(start = 16.dp),
        ) {
            Text(
                text = "성별",
                modifier = Modifier.padding(bottom = 8.dp), // 아래 간격
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        // 단일 선택을 위한 상태 관리 (현재 선택된 인덱스)
        var selectedIndex by remember { mutableStateOf<Int?>(null) }

        val items = listOf(
            "남성",
            "여성"
        )

        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // 버튼 간 간격 조정
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f) // 가로 공간을 균등하게 분배
                        .padding(horizontal = 6.dp)
                        .background(
                            color = if (selectedIndex == index) Color(0xFFE9EAFF) else Color(0xFFF6F6F6),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            // 선택된 인덱스가 현재 인덱스와 같으면 해제, 다르면 새로 선택
                            selectedIndex = if (selectedIndex == index) null else index
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedIndex == index) Color(0xFF6A71FF) else Color(0xFF888888)
                    )
                }
            }
        }
    }


    @Composable
    fun ProfileImagePicker() {
        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var hasPermission by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        // 권한 요청 Launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            hasPermission = isGranted
        }

        // 갤러리 열기 Launcher
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                imageUri = uri // 선택된 이미지 URI 저장
            }
        )

        // 권한 체크 및 요청
        LaunchedEffect(Unit) {
            scope.launch {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        hasPermission = true
                    }

                    else -> {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }
        }

        // UI 렌더링
        if (hasPermission) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                // 프로필 이미지
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    AsyncImage(
                        model = imageUri ?: "https://via.placeholder.com/100", // 기본 이미지 또는 선택된 이미지
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }

                // 플러스 버튼
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            imagePickerLauncher.launch("image/*") // 갤러리 열기
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.btn_plus), // 플러스 아이콘
                        contentDescription = "Add Image",
                        tint = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun ProfileScreen() {
        ProfileImagePicker()
    }

}


@Preview(showBackground = true)
@Composable
fun GrePreview3() {
    SwypTheme {

    }
}