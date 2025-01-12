package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM

@Composable
//陪伴者 看 顧客 刊登項目的頁面
fun CompanionLookPublishScreen(
    navController: NavHostController,
    companionVM: CompanionVM,
    tabVM: TabVM,
    serviceNo: Int
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val detail by companionVM.applicantSelectState.collectAsState()
    Log.d("_tagDetail01", "detail:$detail")
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val memberNo = preferences.getInt("member_no", 0)

    LaunchedEffect(Unit) {
        companionVM.getApplicantSelect(serviceNo, memberNo)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(companionScenery)
    ) { }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        //顧客資(頭像、名字、聊天鈕)
        Row(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .padding(top = 8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        2.dp, Color.DarkGray,
                        CircleShape
                    ),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "memberPhoto",
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "名字：${detail.posterName}",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,//背景顏色
                        contentColor = Color.Black//內容顏色
                    ),
                    border = BorderStroke(1.dp, Color.Black)//外框樣式
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "聊聊", modifier = Modifier.padding(2.dp))
                        Icon(
                            painter = painterResource(R.drawable.chat),
                            contentDescription = "聊聊",
                        )
                    }
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(6.dp))//分隔線
        Text(text = "標題：${detail.service}", fontSize = 24.sp)
        Text(text = "內容：${detail.serviceDetail}", fontSize = 24.sp)
        /*
        Image(//再設最大上限
            painter = painterResource(R.drawable.friendzy),
            contentDescription = "servicImage",
            modifier = Modifier.sizeIn(
                maxWidth = screenWidth * 0.9f,
                maxHeight = screenHeight * 0.3f,
                minWidth = screenWidth * 0.4f,
                minHeight = screenHeight * 0.2f,
            )
        )
         */
        Text(text = "開始時間：${formatTimestamp(detail?.startTime)}", fontSize = 24.sp)
        Text(text = "結束時間：${formatTimestamp(detail?.endTime)}", fontSize = 24.sp)
        Text(text = "所在地區：${detail?.area}", fontSize = 24.sp)
//        Text(text = "金費：xxx元", fontSize = 24.sp)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {

                    }
                ) { Text("預約", fontSize = 24.sp) }
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {

                    }
                ) { Text("再想想", fontSize = 24.sp) }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionLookPublishScreen(
) {
    CompanionLookPublishScreen(
        rememberNavController(),
        companionVM = CompanionVM(),
        tabVM = TabVM(),
        serviceNo = 0
    )
}