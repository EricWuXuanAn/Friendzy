package com.example.tip102group01friendzy.ui.feature.Memberpage

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import kotlin.io.outputStream

@Composable
fun SelectionMenu(
    items: List<String>, // 可供選擇的項目
    tempSelectedItems: MutableSet<String>, // 暫存的選擇清單
    onConfirm: (Set<String>) -> Unit, // 確認按鈕點擊後的處理邏輯，傳遞已選擇的結果
    onClose: () -> Unit // 關閉選擇框的處理邏輯
) {
    var selectedItems by remember { mutableStateOf(tempSelectedItems.toSet()) } // 使用狀態儲存選擇結果

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDCD0F0), shape = RoundedCornerShape(12.dp))
            .padding(8.dp) // 外邊距
    ) {
        // 將項目分為多行，每行最多 3 個
        items.chunked(3).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // 平分每個項目的空間
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = item in selectedItems, // 是否已選擇
                            onCheckedChange = { isChecked ->
                                selectedItems = if (isChecked) {
                                    selectedItems + item // 勾選後新增至暫存清單
                                } else {
                                    selectedItems - item // 取消勾選後移出暫存清單
                                }
                            }
                        )
                        Text(text = item) // 顯示項目名稱
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, // 確認與取消按鈕對齊
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onClose() // 關閉選擇框
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("取消")
            }

            Button(
                onClick = {
                    tempSelectedItems.clear()
                    tempSelectedItems.addAll(selectedItems) // 更新暫存清單
                    onConfirm(tempSelectedItems) // 回傳已選擇的結果
                    onClose() // 關閉選擇框
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("確認")
            }
        }
    }
}

@Composable
fun generateStars(score: Int?): AnnotatedString {
    if (score == null || score < 0) return buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFFBCA1C))) {
            append("☆☆☆☆☆")
        }
    }

    val filledStars = score.coerceAtMost(5) // 滿星部分
    val emptyStars = 5 - filledStars // 空星部分

    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFFBCA1C))) {
            append("★".repeat(filledStars)) // 黃色的滿星
        }
        withStyle(style = SpanStyle(color = Color(0xFFFBCA1C))) {
            append("☆".repeat(emptyStars)) // 灰色的空星
        }
    }
}


@Composable
fun MemberScreen(
    navController: NavHostController,
    memberVM: MemberSceernVM,
) {
    // 觀察 ViewModel 的會員資料
    val memberInfo by memberVM.memberInfo.collectAsState()

    // 使用 remember 保存輸入框的狀態（專長與自我介紹）
    var selectedSpecialties by remember { mutableStateOf(listOf<String>()) } // 已選專長
    var tempSelectedSpecialties by remember { mutableStateOf(mutableSetOf<String>()) } // 暫存選擇專長
    var isSpecialtiesExpanded by remember { mutableStateOf(false) } // 是否展開選單

    // 預設的專長清單
    val specialties = listOf("攝影", "設計", "編程", "翻譯", "教學", "寫作")

    // 城市選項列表與已選城市的狀態
    val cities = listOf(
        "台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
        "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市",
        "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"
    )

    var selectedCities by remember { mutableStateOf(listOf<String>()) } // 已選地區
    var tempSelectedCities by remember { mutableStateOf(mutableSetOf<String>()) } // 暫存選擇地區
    var isCitiesExpanded by remember { mutableStateOf(false) } // 是否展開選單

    // 自我介紹區塊的狀態
    var isEditingSelfIntroduction by remember { mutableStateOf(false) }  // 是否處於編輯自我介紹的狀態
    var tempSelfIntroduction by remember { mutableStateOf("") } // 暫存的自我介紹內容
    var selfIntroduction by remember { mutableStateOf("") } // 自我介紹的最終內容
    var tempProfileImageUri by remember { mutableStateOf<Uri?>(null) } // 暫存頭像圖片 URI

    val context = LocalContext.current

    // **建立圖片挑選啟動器**
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                // **儲存圖片 URI 到 SharedPreferences** (修改部分)
                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("profile_image_uri", uri.toString()).apply()  // 儲存 URI

                // 將圖片存到本地端（如果有需要）
                val fileName = "profile_image_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                tempProfileImageUri = uri // 更新臨時的 URI
            }
        }
    )

    // **建立權限請求啟動器**
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGranted ->
            if (isGranted.any { it.value }) {
                pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(context, "需要存取相片權限才能選擇頭像", Toast.LENGTH_SHORT).show()
            }
        }
    )
// **畫面初始化時，從 SharedPreferences 讀取已選擇的圖片 URI
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val savedUriString = sharedPreferences.getString("profile_image_uri", null)  // 讀取儲存的 URI

// **設定初始的頭像圖片 URI
    val initialProfileImageUri = savedUriString?.let { Uri.parse(it) } // 從 SharedPreferences 讀取 URI

// 設定初始的頭像圖片 URI
    tempProfileImageUri = initialProfileImageUri ?: tempProfileImageUri  // 使用儲存的 URI，若沒有則保持原本的 URI

    // 觀察會員資料，並初始化會員資料
    LaunchedEffect(Unit) {
        memberVM.fetchMemberInfo(context)
    }

    LaunchedEffect(memberInfo) {
        selfIntroduction = memberInfo.introduction ?: ""
    }

    // 初始化服務地區與專長資料
    LaunchedEffect(Unit) {
        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val savedCities = preferences.getStringSet("selectedCities", emptySet()) ?: emptySet()
        val savedSpecialties =
            preferences.getStringSet("selectedSpecialties", emptySet()) ?: emptySet()

        selectedCities = savedCities.toList()
        selectedSpecialties = savedSpecialties.toList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // 頭像區塊
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(tempProfileImageUri ?: R.drawable.friendzy), // 如果有選擇圖片，顯示選擇的圖片，否則顯示預設圖片
                contentDescription = "頭像",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, Color(0x3C645959)), CircleShape)
                    .clickable {
                        // 這裡保持原本的邏輯來選擇圖片
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_MEDIA_IMAGES
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        } else {
                            // 若需要權限，則請求相應權限
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                permissionLauncher.launch(
                                    arrayOf(
                                        READ_MEDIA_IMAGES,
                                        READ_MEDIA_VIDEO,
                                        READ_MEDIA_VISUAL_USER_SELECTED
                                    )
                                )
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(
                                    arrayOf(
                                        READ_MEDIA_IMAGES,
                                        READ_MEDIA_VIDEO
                                    )
                                )
                            } else {
                                permissionLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
                            }
                        }
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 評價區
            Column {
                // 陪伴者評價
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "陪伴者評價：",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = generateStars(memberInfo.companionScore),
                        fontSize = 16.sp,
                        style = TextStyle.Default // 文字樣式（讓星星正常渲染）
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // 分隔線

                // 顧客評價
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "顧客評價：",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = generateStars(memberInfo.customerScore),
                        fontSize = 16.sp,
                        style = TextStyle.Default // 文字樣式（讓星星正常渲染）
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 設定按鈕
            IconButton(onClick = {
                navController.navigate(Screen.SettingScreen.name)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.set),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // 使用 SpaceBetween 讓名稱和按鈕分開
        ) {
            // 顯示會員名稱和ID
            Text(
                text = "${memberInfo.nickname?.takeIf { it.isNotBlank() } ?: memberInfo.name ?: ""}\nNo.${memberInfo.memberNo ?: ""}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // 切換視角的按鈕和文字
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    navController.navigate(Screen.ForOwnScreen.name) // 切換顯示的視角
                }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "切換視角",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp)) // 按鈕和文字之間的間隔

                Text(
                    text = "預覽",
                    style = TextStyle(fontSize = 16.sp, color = Color.Black),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

                Spacer(modifier = Modifier.height(20.dp))

                // 服務地區選擇
                Text(
                    text = "服務地區",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0), shape = RoundedCornerShape(12.dp))
                        .clickable { isCitiesExpanded = true }
                        .padding(16.dp)
                ) {
                    if (selectedCities.isEmpty()) {
                        Text(
                            text = "請選擇服務地區",
                            color = Color.DarkGray
                        )
                    } else {
                        Text(
                            text = selectedCities.joinToString(", ")
                        )
                    }
                }

                if (isCitiesExpanded) {
                    SelectionMenu(
                        items = cities,
                        tempSelectedItems = tempSelectedCities,
                        onConfirm = { selectedItems ->
                            selectedCities = selectedItems.toList()
                            val preferences =
                                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                            preferences.edit().putStringSet("selectedCities", selectedItems).apply()
                        },
                        onClose = { isCitiesExpanded = false }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 專長選擇
                Text(
                    text = "專長",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0), shape = RoundedCornerShape(12.dp))
                        .clickable { isSpecialtiesExpanded = true }
                        .padding(16.dp)
                ) {
                    if (selectedSpecialties.isEmpty()) {
                        Text(
                            text = "請選擇專長",
                            color = Color.DarkGray
                        )
                    } else {
                        Text(
                            text = selectedSpecialties.joinToString(", ")
                        )
                    }
                }

                if (isSpecialtiesExpanded) {
                    SelectionMenu(
                        items = specialties,
                        tempSelectedItems = tempSelectedSpecialties,
                        onConfirm = { selectedItems ->
                            selectedSpecialties = selectedItems.toList()
                            val preferences =
                                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                            preferences.edit().putStringSet("selectedSpecialties", selectedItems)
                                .apply()
                        },
                        onClose = { isSpecialtiesExpanded = false }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 自我介紹區塊
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp) // 確保與其他區塊對齊
                ) {
                    // 自我介紹標題
                    Text(
                        text = "自我介紹",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 判斷是否正在編輯
                    if (isEditingSelfIntroduction) {
                        // 編輯模式
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFDCD0F0),
                                    shape = RoundedCornerShape(12.dp)
                                ) // 與顯示模式相同的外框顏色
                                .padding(4.dp) // 確保內部輸入框有邊距
                        ) {
                            TextField(
                                value = tempSelfIntroduction,
                                onValueChange = { tempSelfIntroduction = it },
                                placeholder = {
                                    Text(
                                        if (memberInfo.introduction.isNullOrBlank()) {
                                            "請輸入您的自我介紹"
                                        } else {
                                            memberInfo.introduction!!
                                        }
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent, // 無焦點背景
                                    unfocusedContainerColor = Color.Transparent, // 非焦點背景
                                    disabledContainerColor = Color.Transparent // 禁用背景
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp)) // 讓輸入框圓角一致
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp)) // 按鈕與輸入框間距

                        // 確認按鈕
                        Button(
                            onClick = {
                                isEditingSelfIntroduction = false
                                memberVM.updateIntroduction(context, tempSelfIntroduction)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("確認")
                        }
                    } else {
                        // 顯示模式
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFDCD0F0),
                                    shape = RoundedCornerShape(12.dp)
                                ) // 背景與圓角
                                .height(200.dp)
                                .clickable {
                                    // 進入編輯模式，並將現有資料填充至 tempSelfIntroduction
                                    isEditingSelfIntroduction = true
                                    tempSelfIntroduction = memberInfo.introduction ?: ""
                                }
                                .padding(16.dp)
                        ) {
                            // 顯示自我介紹內容或提示文字
                            Text(
                                text = if (!memberInfo.introduction.isNullOrBlank()) memberInfo.introduction!! else "點擊編輯您的自我介紹",
                                color = if (memberInfo.introduction.isNullOrBlank()) Color.DarkGray else Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
        }
    }
}






