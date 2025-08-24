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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

class ProfileUpdate : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val sharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

            // SharedPreferences 값 불러오기
            val name = remember { mutableStateOf(sharedPreferences.getString("name", "") ?: "") }
            val birth = remember { mutableStateOf(sharedPreferences.getString("birth", "") ?: "") }
            val gender = remember { mutableStateOf<String?>(sharedPreferences.getString("gender", null)) }
            val selectedImageUri = remember {
                mutableStateOf(sharedPreferences.getString("profileImageUri", null)?.let { Uri.parse(it) })
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.White
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    TopText()
                    InstructionText()
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
                text = "프로필 수정",
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
    fun InstructionText() {
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
                    // 입력 체크
                    if (name.value.isBlank()) {
                        Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (name.value.length == 1) {
                        Toast.makeText(context, "이름은 2자 이상 15자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (birth.value.length != 8) {
                        Toast.makeText(context, "생일은 8자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (gender.value == null) {
                        Toast.makeText(context, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // SharedPreferences 저장
                    saveProfileData(context, name.value, birth.value, gender.value, selectedImageUri.value?.toString())

                    if (context is ComponentActivity) {
                        context.finish()
                    }
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
                if (i < totalSteps) Spacer(modifier = Modifier.width(3.dp))
            }
        }
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
            onValueChange = { name.value = it.take(15) },
            placeholder = { Text("성함을 입력해주세요.", color = Color.LightGray, fontSize = 15.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(55.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 15.sp),
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
                if (input.length <= 8 && input.all { it.isDigit() }) birth.value = input
            },
            placeholder = {
                Text(
                    "생년월일 8자리 입력해주세요. ex)19990909",
                    color = Color.LightGray,
                    fontSize = 15.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(55.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 15.sp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
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
                            color = if (gender.value == item) Color(0xFFE9EAFF) else Color(0xFFF6F6F6),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clickable { gender.value = if (gender.value == item) null else item },
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
            uri?.let {
                try {
                    context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    selectedImageUri.value = it
                    context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
                        .edit().putString("profileImageUri", it.toString()).apply()
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
            Box(modifier = Modifier.size(130.dp)) {
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
                    modifier = Modifier.align(Alignment.BottomEnd).size(40.dp)
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
                            context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
                                .edit().remove("profileImageUri").apply()
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
                    ) { Text("기본 프로필 사용") }
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
                    ) { Text("갤러리") }
                }
            }
        }
    }
}
