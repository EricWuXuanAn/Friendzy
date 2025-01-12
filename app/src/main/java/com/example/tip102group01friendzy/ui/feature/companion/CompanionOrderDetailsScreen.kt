package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM

@Composable
//訂單明細頁面
//圖片要再改
fun CompanionOrderDetailsScreen(
    navController: NavHostController,
    companionOrderVM: CompanionOrderVM,
    tabVM: TabVM,
    orderId: Int,
    poster: Int
) {//評論評分要在處裡
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val memberNo = preferences.getInt("member_no", 0)

    val order by companionOrderVM.orderSelectState.collectAsState()
//    Log.d("_tagDetails","$orderDtl")
    var orderStatus =order?.orderStatus ?:4//訂單狀態
    var rate = 0  //送出評分
    rate = if (order?.posterStatus == 1){
        if (memberNo == order?.orderPoster){
            order?.comRate?:0
        }else{
            order?.cusRate?:0
        }
    }else{
        if (memberNo == order?.orderPoster){
            order?.cusRate?:0
        }else{
            order?.comRate?:0
        }
    }
//    var comment = order?.comRateContent ?:"錯誤"  //評論送出
    var comment  = "" //評論送出
    comment = if (order?.posterStatus == 1){
        if (memberNo == order?.orderPoster){
            order?.comRateContent ?:"error"
        }else{
            order?.cusRateContent ?:"error"
        }
    }else{
        if (memberNo == order?.orderPoster){
            order?.cusRateContent ?:"error"
        }else{
            order?.comRateContent ?:"error"
        }
    }

    var rating by remember { mutableIntStateOf(0) } // 評分輸入
    var inputText by remember { mutableStateOf("") } //評論輸入
    var noScoreText by remember { mutableStateOf("") } //送出沒評分顯示的字
    val statusList = listOf("待確認", "進行中", "已完成", "取消","錯誤")

    val blank = 6.dp
    val testTrue = 1 == 2 //寫code方便看 全顯示用

    LaunchedEffect(Unit) {
        companionOrderVM.getSelectOrder(memberNo,poster,orderId)
        Log.d("_tag details","orderStatus：$orderStatus ,score:$rate ,comment:$comment")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        Row (
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .padding(top = 8.dp)
        ){
            /*
            Image(
                modifier = Modifier.size(120.dp).clip(CircleShape).border(2.dp, Color.DarkGray,
                    CircleShape
                ),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "memberPhoto",
            )
            */
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.End
            ){
                Text(text = "名字：${ order?.theirName }",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp))
            }
        }
        HorizontalDivider(modifier = Modifier.padding(6.dp))//分隔線

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "訂單編號：${order?.orderId}", fontSize = 20.sp)
            Text(text = "標題：${order?.service}", fontSize = 20.sp)
            Text(text = "服務金額：${order?.orderPrice}",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = blank))
            Text(text = "刊登人：${order?.orderPosterName}", fontSize = 20.sp)
            Text(text = "訂購人：${order?.orderPersonName}", fontSize = 20.sp)
            Text(text = "開始時間：${formatTimestamp(order?.startTime)}", fontSize = 20.sp)
            Text(text = "結束時間：${formatTimestamp(order?.endTime)}", fontSize = 20.sp)
            Text(text = "訂單狀態：${statusList[orderStatus]}", fontSize = 20.sp)
            if (orderStatus == 2 && rate != 0 || testTrue) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "評分：", fontSize = 20.sp)
                    Row {
                        for (i in 1..5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star $i",
                                tint = if (i <= rate) Color(0xFFFFD700) else Color.Gray, // 金色或灰色
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
        if (rate == 0 && orderStatus == 2 || testTrue) {
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
                        if (rating == 0) {
                            noScoreText = "請選擇評分數"
                        }else{
                            companionOrderVM.setRate(
                                memberNo,
                                order?.orderPoster!!,
                                order?.posterStatus!!,
                                rating,
                                inputText,
                                order?.orderId!!)
                        }
                        navController.popBackStack()
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
        if (orderStatus != 0 || testTrue){

        }
        Spacer(modifier = Modifier.size(8.dp))//間隔
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (orderStatus == 1 || testTrue) {
                Button(
                    modifier = Modifier
                        .padding(end = blank)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                        companionOrderVM.setOrderStatus(order?.orderId!!,2,memberNo)
                        navController.popBackStack()
                    }
                ) { Text("完成訂單") }
            }
            if (orderStatus == 1 || orderStatus == 0 || testTrue) {
                Button(
                    modifier = if(orderStatus == 1){
                        Modifier.fillMaxWidth(1f)
                    }else{
                        Modifier.fillMaxWidth(0.5f)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                            companionOrderVM.setOrderStatus(order?.orderId!!,3,memberNo)
                            navController.popBackStack()
                    },
                ) { Text("取消訂單") }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun PreviewCompanionOrderDetailsScreen() {
//    CompanionOrderDetailsScreen(rememberNavController(), companionOrderVM = CompanionOrderVM(), tabVM = TabVM())
//}