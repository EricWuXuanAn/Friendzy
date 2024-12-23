package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.ui.feature.Memberpage.MemberSceernVM


@Composable
fun MemberScreen(
    navController: NavHostController,
    memberVM: MemberSceernVM
) {

    // 使用 remember 保存輸入框的狀態（專長與自我介紹）
    // 專長選項列表與已選專長的狀態
    val specialties = listOf(
        "專長1",
        "專長2",
        "專長3",
        "專長4",
        "專長5",
        "專長6",
        "專長7",
        "專長8",
        "專長9",
        "專長10"
    )
    val selectedSpecialties = remember { mutableStateListOf<String>() } // 動態儲存使用者選擇的專長
    var isSpecialtiesExpanded by remember { mutableStateOf(false) } // 控制專長是否展開的狀態

    // 城市選項列表與已選城市的狀態
    val cities = listOf(
        "台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
        "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市",
        "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"
    )
    val selectedCities = remember { mutableStateListOf<String>() } // 動態儲存使用者選擇的城市
    var isCitiesExpanded by remember { mutableStateOf(false) } // 控制服務地區是否展開的狀態

    var selfIntroduction by remember { mutableStateOf("") } // 自我介紹輸入框的狀態

    // 計算平均評價（未來可透過其他頁面傳入評價資料）
    val averageRating by remember { mutableStateOf("") }
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
                Text(
                    text = "陪伴者評價", //${generateStars(averageRating)}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "顧客評價", //${generateStars(averageRating)}",
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

        /// 名稱與聊聊按鈕
        Row(
            modifier = Modifier.fillMaxWidth(), // 佔滿寬度
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 左右間距
        ) {
            // 名稱顯示
            Text(
                text = "暱稱\nNo.123456", // 名稱與編號
                fontWeight = FontWeight.Bold, // 粗體字
                fontSize = 20.sp // 字體大小
            )
            // 聊聊按鈕區塊
            // 聊聊按鈕
            Button(
                onClick = {
                    navController.navigate(Screen.ChatroomScreen.name) // 跳轉到聊聊頁面
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // 設定按鈕背景顏色
                modifier = Modifier.padding(horizontal = 8.dp) // 增加按鈕間距
            ) {
                // 圖標
                Image(
                    painter = painterResource(id = R.drawable.chat), // 自訂圖標資源
                    contentDescription = "Chat", // 無障礙說明
                    modifier = Modifier
                        .size(25.dp) // 設定圖標大小
                        .padding(end = 7.dp) // 圖標和文字間距
                )
                // 文字
                Text(
                    text = "聊聊", // 按鈕文字
                    color = Color.Black, // 文字顏色
                    fontSize = 18.sp // 文字大小
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        // 服務地區區

            Text(
                text = "服務地區",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { isCitiesExpanded = !isCitiesExpanded } // 點擊展開或摺疊
                    .padding(vertical = 8.dp)
            )

            if (isCitiesExpanded) { // 如果展開，顯示可選服務地區
                cities.forEach { city ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = city in selectedCities, // 判斷是否選中
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedCities.add(city) // 新增地區
                                } else {
                                    selectedCities.remove(city) // 移除地區
                                }
                            }
                        )
                        Text(text = city, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
            TextField(
                value = selectedCities.joinToString(", "), // 顯示選中的服務地區
                onValueChange = {},
                readOnly = true, // 設為只讀
                label = { Text("已選服務地區") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )







    Spacer(modifier = Modifier.height(16.dp)) // 間距

    // 專長區
    Text(
        text = "專長",
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable { isSpecialtiesExpanded = !isSpecialtiesExpanded } // 點擊展開或摺疊
            .padding(vertical = 8.dp)
    )
    if (isSpecialtiesExpanded) { // 如果展開，顯示可選專長
        specialties.forEach { specialty ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = specialty in selectedSpecialties, // 判斷是否選中
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedSpecialties.add(specialty) // 新增專長
                        } else {
                            selectedSpecialties.remove(specialty) // 移除專長
                        }
                    }
                )
                Text(text = specialty, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
    TextField(
        value = selectedSpecialties.joinToString(", "), // 顯示選中的專長
        onValueChange = {},
        readOnly = true, // 設為只讀
        label = { Text("已選專長") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))


    // 自我介紹輸入框
    Text(text = "自我介紹", fontWeight = FontWeight.Bold) // 標題
    TextField(
        value = selfIntroduction, // 自我介紹的輸入內容
        onValueChange = { selfIntroduction = it }, // 更新內容
        placeholder = { Text("請輸入您的自我介紹") }, // 提示文字
        modifier = Modifier
            .fillMaxWidth() // 佔滿寬度
            .padding(vertical = 8.dp) // 垂直間距
            .height(120.dp) // 設定高度
    )

    // 刊登中顯示
    Box(
        modifier = Modifier
            .fillMaxWidth() // 佔滿寬度
            .padding(vertical = 8.dp) // 垂直間距
            .height(50.dp) // 設定高度
            .background(Color.LightGray), // 背景顏色
        contentAlignment = Alignment.Center // 內容置中
    ) {
        Text(text = "刊登中", fontWeight = FontWeight.Bold) // 文字內容
    }

    // 增加間距，讓內容向上排列
    Spacer(modifier = Modifier.weight(1f))
}
}

@Composable
fun generateStars(average: Double): String {
    val fullStars = average.toInt() // 滿星數
    val hasHalfStar = average - fullStars >= 0.5 // 是否有半星

    var stars = "⭐".repeat(fullStars) // 重複顯示滿星
    if (hasHalfStar) {
        stars += "⭐" // 半星以額外一顆星表示
    }

    return stars
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    val navController = rememberNavController()
//    val ratings = listOf(5, 4, 5, 3, 4) // 模擬評價數據
//    ProfileScreen(navController, ratings)
//}

@Composable
@Preview(showBackground = true)
fun memberScreenPreview() {
    MemberScreen(rememberNavController(), memberVM = MemberSceernVM())
}