package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R

@Composable
fun ReservationScreen(
    navController: NavHostController,
    reservationVM: ReservationVM,
    postListVM: PostListVM

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Check Him/Her Out",
            fontSize = 25.sp,
            fontFamily = FontFamily.Cursive
        )
        HorizontalDivider()
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
//            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(5.dp)
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "image"
            )
            Text(
                text = "Name: ",
                modifier = Modifier.padding(10.dp),
                fontSize = 18.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.chat),
                        contentDescription = "chat"
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(10.dp)
        ) {
            Text(text = "TiTle: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Specialty: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Available: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Start: YYYY-MM-DD   HH",fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Start: YYYY-MM-DD   HH", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Location: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Price: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
        }
    }
    Row(
        modifier = Modifier.padding(20.dp).fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(onClick = {
            //發請求給後端 叫後端把我的 reservationState 從false 改成true
        }) {
            Text(text = "Confirm")
        }
        Button(onClick = { }) {
            Text(text = "Think again")
        }
    }
}



@Composable
@Preview(showBackground = true)
fun ReservationScreenPreview() {
    ReservationScreen(rememberNavController(), ReservationVM(), PostListVM())
}