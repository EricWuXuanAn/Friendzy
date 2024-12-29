package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM


@Composable
//陪伴者確認預約(確認訂單)
fun CompanionCheckAppointmentScreen(
    navController: NavHostController,
    tabVM: TabVM
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //預約人的資訊
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
                    text = "名字：${""}",
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
        Column (
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = "我刊登的資訊", fontSize = 28.sp, modifier = Modifier.fillMaxWidth())
            Text(text = "標題：", fontSize = 20.sp)
            Text(text = "開始時間：", fontSize = 20.sp)
            Text(text = "結束時間：", fontSize = 20.sp)
            Text(text = "服務地區：", fontSize = 20.sp)
        }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.padding(end = 4.dp).fillMaxWidth(0.5f),
                    onClick = {

                    }
                ) { Text("確認") }
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = {

                    },
                ) { Text("拒絕") }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionCheckAppointmentScreen(
) {
    CompanionCheckAppointmentScreen(rememberNavController(),tabVM = TabVM())
}