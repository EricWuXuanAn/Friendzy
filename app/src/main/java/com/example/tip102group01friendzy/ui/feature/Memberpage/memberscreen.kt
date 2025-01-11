package com.example.tip102group01friendzy.ui.feature.Memberpage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM


@Composable
fun SelectionMenu(
    items: List<String>, // 可供選擇的項目
    tempSelectedItems: MutableSet<String>, // 暫存的選擇清單
    onConfirm: () -> Unit // 確認按鈕點擊後的處理邏輯
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDCD0F0)) // 設定背景顏色
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
                            checked = item in tempSelectedItems, // 是否已選擇
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    tempSelectedItems.add(item) // 勾選後新增至暫存清單
                                } else {
                                    tempSelectedItems.remove(item) // 取消勾選後移出暫存清單
                                }
                            }
                        )
                        Text(text = item) // 顯示項目名稱
                    }
                }
            }
        }

        Button(
            onClick = onConfirm, // 點擊後執行確認邏輯
            modifier = Modifier.align(Alignment.End) // 按鈕靠右對齊
        ) {
            Text("確認")
        }
    }
}

fun generateStars(score: Int?): String {
    if (score == null || score < 0) return "☆☆☆☆☆"
    val filledStars = "★".repeat(score.coerceAtMost(5)) // 滿星部分
    val emptyStars = "☆".repeat(5 - score.coerceAtMost(5)) // 空星部分
    return filledStars + emptyStars
}

@Composable
fun MemberScreen(
    navController: NavHostController,
    memberVM: MemberSceernVM
) {
    // 觀察 ViewModel 的會員資料
    val memberInfo by memberVM.memberInfo.collectAsState()
    // 使用 remember 保存輸入框的狀態（專長與自我介紹）
    // 使用 MutableState 管理狀態
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
    // 使用 MutableState 管理狀態
    var selectedCities by remember { mutableStateOf(listOf<String>()) } // 已選地區
    var tempSelectedCities by remember { mutableStateOf(mutableSetOf<String>()) } // 暫存選擇地區
    var isCitiesExpanded by remember { mutableStateOf(false) } // 是否展開選單
    // 暫存選擇項目
    var isEditingSelfIntroduction by remember { mutableStateOf(false) }  // 是否處於編輯自我介紹的狀態
    var tempSelfIntroduction by remember { mutableStateOf("") } // 暫存的自我介紹內容
    var selfIntroduction by remember { mutableStateOf("") } // 自我介紹的最終內容

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        // 初始化時從後端抓取會員資料
        memberVM.fetchMemberInfo(context)
    }
    LaunchedEffect(memberInfo) {
        selfIntroduction = memberInfo.introduction ?: ""
    }
    // 初始化時載入儲存的服務地區資料
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
            .verticalScroll(rememberScrollState()) // 支援垂直捲動
            .padding(16.dp) // 外邊距
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直居中對齊
            horizontalArrangement = Arrangement.SpaceBetween, // 元件之間保持間距
            modifier = Modifier.fillMaxWidth()
        ) {
            // 頭像
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Avatar",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(40.dp)
                )
            }
            // 評價區 - 平均評價

            Column {
                // 陪伴者評價
                Text(
                    text = "陪伴者評價：${generateStars(memberInfo.companionScore)}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                // 顧客評價
                Text(
                    text = "顧客評價：${generateStars(memberInfo.customerScore)}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            // 設定按鈕
            IconButton(onClick = {
                navController.navigate(Screen.SettingScreen.name) // 跳轉到設定頁面
            }) {
                // 使用自訂圖標替換預設 Icons.Default.Settings
                Image(
                    painter = painterResource(id = R.drawable.set), // 自訂圖標
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp), // 設定圖標大
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        /// 名稱
        Row(
            modifier = Modifier.fillMaxWidth(), // 佔滿寬度
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 左右間距
        ) {
            // 名稱顯示
            // 顯示會員暱稱或名字與編號
            Text(
                text = "${memberInfo.nickname?.takeIf { it.isNotBlank() } ?: memberInfo.name ?: ""}\nNo.${memberInfo.memberNo ?: ""}", // 暱稱或名字與編號
                fontWeight = FontWeight.Bold, // 粗體字
                fontSize = 20.sp, // 字體大小
                modifier = Modifier.padding(vertical = 8.dp) // 上下間距
            )

        }

        Spacer(modifier = Modifier.height(16.dp))


        // 服務地區區
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // 充滿整個畫面
                    .padding(16.dp) // 外邊距
            ) {
                // 服務地區區塊
                Text(
                    text = "服務地區",
                    fontWeight = FontWeight.Bold, // 粗體字
                    modifier = Modifier.padding(bottom = 8.dp) // 與下一個元素的間距
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // 充滿父容器的寬度
                        .background(Color(0xFFDCD0F0)) // 設定背景顏色
                        .clickable { isCitiesExpanded = true } // 點擊後展開選單
                        .padding(16.dp) // 內邊距
                ) {
                    if (selectedCities.isEmpty()) {
                        // 若未選擇任何服務地區，顯示提示文字
                        Text(
                            text = "請選擇服務地區",
                            color = Color.DarkGray
                        )
                    } else {
                        // 顯示已選擇的服務地區
                        Text(
                            text = selectedCities.joinToString(", ") // 將清單轉為逗號分隔的字串
                        )
                    }
                }

                if (isCitiesExpanded) {
                    // 若選單展開，顯示選單內容
                    SelectionMenu(
                        items = cities, // 傳入服務地區清單
                        tempSelectedItems = tempSelectedCities, // 暫存的選擇清單
                        onConfirm = {
                            selectedCities = tempSelectedCities.toList() // 更新已選擇的服務地區
                            val preferences =
                                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                            preferences.edit()
                                .putStringSet(
                                    "selectedCities",
                                    tempSelectedCities
                                ) // 儲存至 SharedPreferences
                                .apply()
                            isCitiesExpanded = false // 關閉選單
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // 增加空白間距

                // 專長區塊
                Text(
                    text = "專長",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0))
                        .clickable { isSpecialtiesExpanded = true }
                        .padding(16.dp)
                ) {
                    if (selectedSpecialties.isEmpty()) {
                        // 若未選擇任何專長，顯示提示文字
                        Text(
                            text = "請選擇專長",
                            color = Color.DarkGray
                        )
                    } else {
                        // 顯示已選擇的專長
                        Text(
                            text = selectedSpecialties.joinToString(", ")
                        )
                    }
                }

                if (isSpecialtiesExpanded) {
                    // 若選單展開，顯示選單內容
                    SelectionMenu(
                        items = specialties, // 傳入專長清單
                        tempSelectedItems = tempSelectedSpecialties, // 暫存的選擇清單
                        onConfirm = {
                            selectedSpecialties = tempSelectedSpecialties.toList() // 更新已選擇的專長
                            val preferences =
                                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                            preferences.edit()
                                .putStringSet(
                                    "selectedSpecialties",
                                    tempSelectedSpecialties
                                ) // 儲存至 SharedPreferences
                                .apply()
                            isSpecialtiesExpanded = false // 關閉選單
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // 自我介紹區塊
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // 標題
                Text(
                    text = "自我介紹",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 判斷是否正在編輯
                if (isEditingSelfIntroduction) {
                    // 編輯模式
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                            .padding(8.dp)
                    ) {
                        // 自我介紹輸入框
                        TextField(
                            value = tempSelfIntroduction, // 暫存輸入內容
                            onValueChange = { tempSelfIntroduction = it }, // 更新暫存內容
                            placeholder = { Text("請輸入您的自我介紹") }, // 提示文字
                            modifier = Modifier
                                .fillMaxWidth() // 佔滿寬度
                                .height(120.dp) // 設定高度
                                .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                        )

                        // 確認按鈕
                        Button(
                            onClick = {

                                isEditingSelfIntroduction = false // 結束編輯模式

                                // 傳送更新資料到後端
                                memberVM.updateIntroduction(context, tempSelfIntroduction)
                            },
                            modifier = Modifier.align(Alignment.End) // 按鈕靠右
                        ) {
                            Text("確認")
                        }
                    }
                } else {
                    // 顯示模式
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                            .height(180.dp) // 設定高度
                            .clickable { isEditingSelfIntroduction = true } // 點擊切換為編輯模式
                            .padding(16.dp) // 內邊距
                    ) {
                        // 顯示自我介紹內容或提示文字
                        Text(
                            text = if (!memberInfo.introduction.isNullOrBlank()) memberInfo.introduction!! else "點擊編輯您的自我介紹",
                            color = if (memberInfo.introduction.isNullOrBlank()) Color.DarkGray else Color.Black
                        )

                    }
                }
            }


            // 增加間距，讓內容向上排列
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}




