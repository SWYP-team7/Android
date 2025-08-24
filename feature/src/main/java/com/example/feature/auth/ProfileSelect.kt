package com.example.feature.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.feature.R

class ProfileSelect : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.White
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    val name = remember { mutableStateOf("") }
                    val birth = remember { mutableStateOf("") }
                    val gender = remember { mutableStateOf<String?>(null) }
                    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

                    TopText()
                    ProgressScreen()
                    Text()
                    GalleryImageButton(selectedImageUri)
                    nameTextField(name)
                    birthTextField(birth)
                    selectGender(gender)
                    nextButton(name, birth, gender, selectedImageUri)
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
    fun Text() {
        Column(
            modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "나의 프로필을 설정해주세요.",
                modifier = Modifier.padding(bottom = 8.dp),
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }

    @Composable
    fun nextButton(
        name: MutableState<String>,
        birth: MutableState<String>,
        gender: MutableState<String?>,
        selectedImageUri: MutableState<Uri?>
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                onClick = {
                    val lenName = name.value.length
                    val lenBirth = birth.value.length
                    val selectedGender = gender.value

                    // 입력 검증
                    if (lenName == 0) {
                        Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else if (lenName == 1) {
                        Toast.makeText(context, "이름은 2자 이상 15자 이하로 입력해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    } else if (lenBirth == 0) {
                        Toast.makeText(context, "생일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else if (lenBirth < 8) {
                        Toast.makeText(context, "생일은 8자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else if (selectedGender == null) {
                        Toast.makeText(context, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // 데이터 저장
                    saveProfileData(
                        context,
                        name.value,
                        birth.value,
                        selectedGender,
                        selectedImageUri.value?.toString()
                    )

                    // Intent로 데이터 전달
                    val intent = Intent(context, ProfileKeyword1::class.java).apply {
                        putExtra("name", name.value)
                        putExtra("birth", birth.value)
                        putExtra("gender", selectedGender)
                        putExtra("profileImageUri", selectedImageUri.value?.toString())
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A71FF),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text("다음", fontSize = 15.sp)
            }
        }
    }

    private fun saveProfileData(
        context: Context,
        name: String,
        birth: String,
        gender: String?,
        profileImageUri: String?
    ) {
        val sharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("birth", birth)
            putString("gender", gender)
            putString("profileImageUri", profileImageUri)
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
                if (i < totalSteps) {
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        }
    }

    @Composable
    fun ProgressScreen() {
        StepProgressIndicator(currentStep = 1, totalSteps = 4)
    }

    @Composable
    fun nameTextField(name: MutableState<String>) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "이름",
                modifier = Modifier.padding(bottom = 8.dp),
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        TextField(
            value = name.value,
            onValueChange = { newText ->
                name.value = newText.take(15)
            },
            placeholder = {
                Text("성함을 입력해주세요.", color = Color.LightGray, fontSize = 15.sp)
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
            singleLine = true
        )
    }

    @Composable
    fun birthTextField(birth: MutableState<String>) {
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
            value = birth.value,
            onValueChange = { input ->
                if (input.length <= 8 && input.all { it.isDigit() }) {
                    birth.value = input
                }
            },
            placeholder = {
                Text(
                    "생년월일 8자리 입력해주세요. ex)19990909",
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 3.dp)
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
    fun selectGender(gender: MutableState<String?>) {
        Row(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "성별",
                modifier = Modifier.padding(bottom = 8.dp),
                style = TextStyle(color = Color.Black),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        val items = listOf("남성", "여성")
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                        .background(
                            color = if (gender.value == item) Color(0xFFE9EAFF) else Color(
                                0xFFF6F6F6
                            ),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            gender.value = if (gender.value == item) null else item
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (gender.value == item) Color(0xFF6A71FF) else Color(0xFF888888)
                    )
                }
            }
        }
    }

    @Composable
    fun GalleryImageButton(selectedImageUri: MutableState<Uri?>) {
        var showDialog by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                try {
                    // 영구 권한 부여
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    selectedImageUri.value = uri

                    // SharedPreferences 저장
                    val sharedPreferences =
                        context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("profileImageUri", uri.toString()).apply()

                    Toast.makeText(context, "프로필 사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "이미지 선택 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(130.dp)
            ) {
                if (selectedImageUri.value != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri.value),
                        contentDescription = "선택된 프로필 이미지",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
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
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
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
                            selectedImageUri.value = null
                            val sharedPreferences =
                                context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().remove("profileImageUri").apply()
                            showDialog = false
                        },
                        onGallerySelect = { launcher.launch("image/*") }
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
                    Button(
                        onClick = {
                            onDefaultSelect()
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A71FF),
                            contentColor = Color.White
                        )
                    ) {
                        Text("기본 프로필 사용")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onGallerySelect()
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A71FF),
                            contentColor = Color.White
                        )
                    ) {
                        Text("갤러리")
                    }
                }
            }
        }
    }
}