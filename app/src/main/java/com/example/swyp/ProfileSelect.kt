package com.example.swyp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TextButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
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
                    containerColor = Color.White,
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)

                    ) {
                        TopText()
                        ProgressScreen()
                        Text()
                        GalleryImageButton()
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
                    val intent = Intent(context, ProfileKeyword1::class.java)
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
            placeholder = {
                Text(
                    "성함을 입력해주세요.",
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 3.dp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(55.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 15.sp),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            // 여기가 핵심
            singleLine = true,
        )

    }


    @Composable
    fun birthTextField() {
        // 상태 관리
        val text = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "생년월일",
                modifier = Modifier.padding(bottom = 8.dp),
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        TextField(
            value = text.value,
            onValueChange = { input ->
                // 숫자만 입력 가능 + 최대 8자리 제한
                if (input.length <= 8 && input.all { it.isDigit() }) {
                    text.value = input
                }
            },
            placeholder = {
                Text(
                    "생년월일 8자리 입력해주세요. ex)19990909",
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 3.dp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(55.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 15.sp),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                            color = if (selectedIndex == index) Color(0xFFE9EAFF) else Color(
                                0xFFF6F6F6
                            ),
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
    fun GalleryImageButton(

    ) {
        var showDialog by remember { mutableStateOf(false) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        // 갤러리 열기 런처
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Box로 겹치기
            Box(
                modifier = Modifier.size(130.dp), // Box 크기를 프로필 크기와 동일하게 설정
            ) {
                // 프로필 이미지
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "선택된 프로필 이미지",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop // 원형 안을 꽉 채움
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile_select_view),
                        contentDescription = "기본 프로필 이미지",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                }

                // 오른쪽 아래 갤러리 버튼 (프로필 이미지 기준으로 위치)
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Box(=프로필) 기준 오른쪽 아래
                        .size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_gallery_img_btn),
                        contentDescription = "갤러리 열기",
                        modifier = Modifier.size(40.dp)
                    )
                }

                if (showDialog) {
                    ProfileSelectDialog(
                        onDismiss = { showDialog = false },
                        onDefaultSelect = {
                            selectedImageUri = null // 기본 프로필로 초기화
                        },
                        onGallerySelect = {
                            launcher.launch("image/*") // 갤러리 열기
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun ProfileSelectDialog(
        onDismiss: () -> Unit,
        onDefaultSelect: () -> Unit,
        onGallerySelect: () -> Unit
    ) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("프로필 사진 선택", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(16.dp))

                    // 기본 프로필 버튼
                    Button(
                        onClick = {
                            onDefaultSelect()
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A71FF), // 주황색    // 버튼 배경색
                            contentColor = Color.White       // 텍스트/아이콘 색
                        )
                    ) {
                        Text("기본 프로필 사용")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 갤러리 선택 버튼
                    Button(
                        onClick = {
                            onGallerySelect()
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A71FF), // 주황색    // 버튼 배경색
                            contentColor = Color.White       // 텍스트/아이콘 색
                        )
                    ) {
                        Text("갤러리")
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GrePreview3() {
    SwypTheme {

    }
}