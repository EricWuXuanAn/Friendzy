package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM

@Composable
//訂單明細頁面
//圖片要再改
fun CompanionOrderDetailsScreen(
    navController: NavHostController,
    companionOrderVM: CompanionOrderVM,
    tabVM: TabVM
) {//評論評分要在處裡
    val orderDtl by companionOrderVM.orderDetailsSelectState.collectAsState()
//    Log.d("_tagDetails","$orderDtl")
    var orderStatus by remember { mutableIntStateOf(orderDtl?.orderStatus ?:0) }//訂單狀態
    var rating by remember { mutableIntStateOf(0) } // 評分輸入
    var score by remember { mutableIntStateOf(orderDtl?.comRate ?: 0) }  //送出評分
    var noScoreText by remember { mutableStateOf("") } //送出沒評分顯示的字
    var inputText by remember { mutableStateOf("") } //評論輸入
    var comment by remember { mutableStateOf(orderDtl?.comRateContent ?:"") }  //評論送出
    val statusList = listOf("待確認", "進行中", "已完成", "取消")

    val context = LocalContext.current
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val memberNo = preferences.getInt("member_no", 0)

    val blank = 6.dp
    val testTrue = 1 == 2 //寫code方便看 全顯示用

    Column (
        modifier = Modifier.fillMaxSize()
            .background(companionScenery)
    ){  }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(companionScenery),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //顧客資(頭像、名字、聊天鈕)
        Text(
            text = "對方",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        Row (
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .padding(top = 8.dp)
        ){
            Image(
                modifier = Modifier.size(120.dp).clip(CircleShape).border(2.dp, Color.DarkGray,
                    CircleShape
                ),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "memberPhoto",
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.End
            ){
                Text(text = "名字：${ orderDtl?.theirName }",
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp))
            }
        }
        HorizontalDivider(modifier = Modifier.padding(6.dp))//分隔線
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "訂單編號：${orderDtl?.orderId}", fontSize = 20.sp)
            Text(text = "標題：${orderDtl?.service}", fontSize = 20.sp)
//            Text(text = "服務金額：",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(bottom = blank))
            Text(text = "刊登人：${orderDtl?.orderPosterName}", fontSize = 20.sp)
            Text(text = "訂購人：${orderDtl?.orderPersonName}", fontSize = 20.sp)
            Text(text = "開始時間：${formatTimestamp(orderDtl?.startTime)}", fontSize = 20.sp)
            Text(text = "結束時間：${formatTimestamp(orderDtl?.endTime)}", fontSize = 20.sp)
            Text(text = "訂單狀態：${statusList[orderStatus]}", fontSize = 20.sp)
            if (orderStatus == 2 && score != 0 || testTrue) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "評分：", fontSize = 20.sp)
                    Row {
                        for (i in 1..5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star $i",
                                tint = if (i <= score) Color(0xFFFFD700) else Color.Gray, // 金色或灰色
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
                Text(text = "評論：${if (comment == "") "你沒填寫" else comment}", fontSize = 20.sp)
            }
        }
        //評論、評分送出隱藏
        if (score == 0 && orderStatus == 2 || testTrue) {
            Spacer(modifier = Modifier.size(8.dp))//間隔
            //評分星星
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray, // 金色或灰色
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .clickable {
                                rating = i // 更新評分為點擊的星星數
                            }
                    )
                }
            }
            //評論
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "評論",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = blank)
                )
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = blank)
                )
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = {
                        score = rating
                        comment = inputText
                        if (score == 0) noScoreText = "請選擇評分數"
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                ) {
                    Text(
                        "送出",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = blank)
                    )
                }
                Text(text = noScoreText, fontSize = 28.sp)
            }
        }

        Spacer(modifier = Modifier.size(8.dp))//間隔
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (!(orderStatus == 2 || orderStatus == 3) || testTrue) {
                Button(
                    modifier = Modifier
                        .padding(end = blank)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                        if (orderStatus != 2) {
                            orderStatus = 2
                            companionOrderVM.setOrderStatus(orderDtl?.orderId!!,orderStatus,memberNo)
                        }
                    }
                ) { Text("完成訂單") }
            }
            if (orderStatus == 0 || testTrue) {
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                        if (orderStatus != 3) {
                            orderStatus = 3
                        }
                    },
                ) { Text("取消訂單") }
            }
        }
        /*
        if (orderStatus == 3 || score != 0 && orderStatus == 2)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
                Button(
                    modifier = Modifier
                        .padding(end = blank)
                        .fillMaxWidth(0.5f),
                    onClick = {

                    }
                ) { Text("收藏") }
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = {

                    },
                ) { Text("黑名單") }
        }
         */
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionOrderDetailsScreen() {
    CompanionOrderDetailsScreen(rememberNavController(), companionOrderVM = CompanionOrderVM(), tabVM = TabVM())
}