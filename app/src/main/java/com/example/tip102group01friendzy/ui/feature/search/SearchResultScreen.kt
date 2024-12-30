package com.example.tip102group01friendzy.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter

@Composable
fun SearchResultScreen(
    navController: NavHostController,

) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Back Icon and Search Bar Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 20.dp)
        ) {
//            Icon(
//                imageVector = Icons.Filled.ArrowBack,
//                contentDescription = "Back",
//                modifier = Modifier
//                    .size(30.dp)
//                    .clickable { onBackPressed() }
//            )
            Spacer(modifier = Modifier.width(2.dp))
            SearchBar(
                placeholderText = "Enter your current address",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // SearchBar下方的空間高度

        // Tabs for "找陪伴者" and "找案件"
        ExposedTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        // Display content based on selected tab
        when (selectedTab) {
            0 -> CompanionContent()
            1 -> ClientContent()
        }
    }
}

@Composable
fun ExposedTabs(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTab) {
        Tab(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        ) {
            Text(text = "找陪伴", modifier = Modifier.padding(15.dp))
        }
        Tab(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        ) {
            Text(text = "找案件", modifier = Modifier.padding(15.dp))
        }
    }
}

@Composable
fun CompanionContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "陪伴服務人員",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        TopicsList(
            topics = listOf(
                CompanionInfo(
                    "你的安卓開發專家!",
                    4.8f,
                    "高CP值!知識一輩子帶著走!高效指導你的學習路徑。",
                    "小明",
                    4.5f,
                    120
                ),
                CompanionInfo("毛茸茸療癒服務", 5.0f, "提供溫暖與療癒的陪伴。", "小華", 5.0f, 200),
                CompanionInfo(
                    "健身教練",
                    4.9f,
                    "專業私人健身指導，幫助你達成健身目標。",
                    "阿健",
                    4.9f,
                    95
                ),
                CompanionInfo("心靈導師", 4.7f, "幫助你應對壓力，增強自信。", "心老師", 4.7f, 150),
                CompanionInfo(
                    "語言交換夥伴",
                    4.6f,
                    "提升你的英語或其他語言能力。",
                    "小語",
                    4.6f,
                    80
                ),
                CompanionInfo("烹飪達人", 5.0f, "手把手教你製作美味餐點。", "小廚", 5.0f, 300),
                CompanionInfo(
                    "藝術陪伴",
                    4.8f,
                    "與你分享藝術的世界，創造美好的體驗。",
                    "小藝",
                    4.8f,
                    170
                ),
                CompanionInfo(
                    "寵物陪伴",
                    5.0f,
                    "溫柔的狗狗或貓咪陪伴你度過溫馨時光。",
                    "小毛",
                    5.0f,
                    220
                ),
                CompanionInfo("自然探索", 4.9f, "帶你探索大自然，享受戶外樂趣。", "阿森", 4.9f, 110)
            ),
            onTopicClicked = { topic -> println("你點擊了: ${topic.name}") }
        )
    }
}

@Composable
fun ClientContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "業主需求",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        TopicsList(
            topics = listOf(
                CompanionInfo("餐廳A的美食服務", 4.8f, "快速外送服務。", "餐廳A", 4.7f, 50),
                CompanionInfo("商店B的商品", 5.0f, "高品質的商品。", "商店B", 5.0f, 100)
            ),
            onTopicClicked = { topic -> println("你點擊了: ${topic.name}") }
        )
    }
}

@Composable
fun SearchBar(placeholderText: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFF6ECF8), // Light purple background
                shape = RoundedCornerShape(50.dp) // Fully rounded corners
            )
            .height(48.dp), // Fixed height for the search bar
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = placeholderText,
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TopicsList(topics: List<CompanionInfo>, onTopicClicked: (CompanionInfo) -> Unit) {
    Column {
        topics.forEach { topic ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTopicClicked(topic) }
                    .padding(12.dp)
                    .background(Color.Transparent, RoundedCornerShape(20.dp)) // 背景內部
                    .border(0.3.dp, Color.LightGray, RoundedCornerShape(20.dp)), // 邊框
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 左側的用戶大頭照
                Image(
                    painter = rememberImagePainter("https://example.com/avatar.jpg"), // 替換為實際 URL 或資源
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray) // 若圖片加載失敗的預設背景
                )
                Spacer(modifier = Modifier.width(16.dp))
                // 中間的文本內容
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = topic.name,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp)) // 新增的間距
                    Text(
                        text = topic.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = "陪伴者: ${topic.memberName}", fontSize = 14.sp)
                    CompanionRating(topic.companionAvgRating, topic.ratingCount)
                }
            }
        }
    }
}

@Composable
fun CompanionRating(companionAvgRating: Float, ratingCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        // 星星圖示
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star Icon",
            tint = Color(0xFFFFA500), // 橘黃色星星
            modifier = Modifier.size(20.dp) // 設定圖示大小
        )
        Spacer(modifier = Modifier.width(4.dp)) // 星星與評分的間距

        // 評分文字
        Text(
            text = String.format("%.1f", companionAvgRating),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        // 評分人數
        Spacer(modifier = Modifier.width(4.dp)) // 評分與人數的間距
        Text(
            text = "(${if (ratingCount >= 100) "100+" else ratingCount.toString()})",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

data class CompanionInfo(
    val name: String,
    val rating: Float,
    val description: String,
    val memberName: String,
    val companionAvgRating: Float,
    val ratingCount: Int
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchResultScreenPreview() {
    MaterialTheme {
        SearchResultScreen(rememberNavController())
    }
}
