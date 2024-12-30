package com.example.tip102group01friendzy.ui.feature.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme


@Composable
fun ChatMessageScreen(navController: NavHostController ) {
    var chatMessage by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            OutlinedTextField(
                value = chatMessage,
                onValueChange = { chatMessage = it },
                label = { Text(text = stringResource(R.string.input)) },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.send),
                        contentDescription = "send",
                        modifier = Modifier.clickable {
                            //把訊息發出去
                        }
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessageScreenPreview() {
    TIP102Group01FriendzyTheme {
        ChatMessageScreen(rememberNavController())
    }
}