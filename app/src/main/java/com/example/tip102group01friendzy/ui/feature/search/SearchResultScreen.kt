package com.example.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tip102group01friendzy.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SearchScreen(onBackPressed = { onBackPressedDispatcher.onBackPressed() })
            }
        }
    }
}

@Composable
fun SearchScreen(onBackPressed: () -> Unit) {
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
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onBackPressed() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SearchBar(
                placeholderText = "Enter your current address",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs for "找陪伴者" and "找案件"
        ExposedTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        // Display content based on selected tab
        when (selectedTab) {
            0 -> CompanionContent()
            1 -> OwnerContent()
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
                CompanionInfo("你的安卓開發專家!", R.drawable.avatar10, 4.8f, "專業指導你的學習路徑。"),
                CompanionInfo("毛茸茸療癒服務", R.drawable.avatar14, 5.0f, "提供溫暖與療癒的陪伴。")
            ),
            onTopicClicked = { topic -> println("你點擊了: ${topic.name}") }
        )
    }
}

@Composable
fun OwnerContent() {
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
                CompanionInfo("餐廳A的美食服務", R.drawable.avatar3, 4.8f, "快速外送服務。"),
                CompanionInfo("商店B的商品", R.drawable.avatar5, 5.0f, "高品質的商品。")
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
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = topic.avatarResId),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = topic.name, fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = topic.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

data class CompanionInfo(
    val name: String,
    val avatarResId: Int,
    val rating: Float,
    val description: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSearchScreen() {
    MaterialTheme {
        SearchScreen(onBackPressed = {})
    }
}
