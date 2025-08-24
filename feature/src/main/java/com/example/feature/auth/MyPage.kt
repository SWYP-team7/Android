package com.example.feature.auth

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MyPage : ComponentActivity() {

    private var name by mutableStateOf("")
    private var profileImageUri by mutableStateOf<String?>(null)
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
                    TopText()
                    Profile()
                    MyPageSelect()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        name = sharedPreferences.getString("name", "") ?: ""
        profileImageUri = sharedPreferences.getString("profileImageUri", null)
    }

    @Composable
    fun TopText() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "마이페이지",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }


    @Composable
    fun Profile() {
        val context = LocalContext.current
        val bitmap by remember(profileImageUri) {
            mutableStateOf(profileImageUri?.let { uri ->
                context.contentResolver.openInputStream(Uri.parse(uri))
                    ?.use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
            })
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!,
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_select_view),
                    contentDescription = "기본 프로필 이미지",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = name.ifEmpty { "이름 없음" },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        val intent = Intent(context, ProfileUpdate::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)), // ← 테두리 추가
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("프로필 수정", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

            }
        }
    }

    @Composable
    fun MyPageSelect() {
        Column {
            Divider(color = Color(0xFFF6F6F6), thickness = 5.dp)

            MyPageRow("프로필 키워드 수정")
            Divider(color = Color(0xFFF6F6F6), thickness = 1.dp)

            MyPageRow("카드 저장함")
            MyPageRow("로그아웃")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("회원탈퇴", fontSize = 14.sp, color = Color.LightGray)
            }
        }
    }


    @Composable
    fun MyPageRow(title: String) {
        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 14.sp, color = Color.Black)
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
            val intent = Intent(context, ProfileKeyword1Update::class.java)
            context.startActivity(intent)

        }
    }
}
