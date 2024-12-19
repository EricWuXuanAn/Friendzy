package com.example.tip102group01friendzy.ui.feature.companion

import android.icu.text.Transliterator.Position
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen

//tab選項內容格式
class Tabs(var name: String = "",var btIcon: Int = R.drawable.order_manage)

@Composable
fun CompanionScreen(
    navController: NavHostController = rememberNavController(),
    companionVM: CompanionVM,
    ){
    var inputText by remember { mutableStateOf("") }//搜尋內容
    var tabIndex by remember { mutableIntStateOf(0) }
    var testiten by remember { mutableStateOf("") }//測試用
    var tabs :List<Tabs> =listOf(//tab選項內容
        Tabs("訂單管理",R.drawable.order_manage),
        Tabs("可約時間",R.drawable.date_range),
        Tabs("申請項目",R.drawable.check_list)
    )
    //tab選項點擊功能
    when(tabIndex){
        0 ->{//訂單管理
            testiten = "123"
        }
        1 ->{//可預約時間
            testiten = "456"
        }
        2 ->{//申請項目
            testiten = "asd"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {inputText = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Clear,
                    contentDescription ="Clear",
                    modifier = Modifier.clickable { inputText = "" }
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
        )
        HorizontalDivider()
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.fillMaxWidth(),
            //目前選擇選項的底線
            indicator = {tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.White
                )
            }, //分隔線屬性
            divider = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
            },
        ) {
            tabs.forEachIndexed{index,tabList ->
                Tab(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(6.dp),
                    selected = index == tabIndex,
                    onClick = {tabIndex = index},
                ){//選項樣式
                    Icon(
                        painter = painterResource(tabList.btIcon),
                        contentDescription = tabs[index].name,
                    )
                    Text(text = tabs[index].name)
                    }
                }
            }
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("推薦項目", fontSize = 28.sp)
            Text(text = testiten)
        }
    }
}




@Composable
@Preview(showBackground = true)
fun PreviewCompanionScreen(){
    CompanionScreen(rememberNavController(), companionVM = CompanionVM())
}