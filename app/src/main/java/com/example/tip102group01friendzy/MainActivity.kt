package com.example.tip102group01friendzy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import java.net.CookieManager
import java.net.CookiePolicy


class MainActivity : ComponentActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val memberToken = preferences.getString("member_token", null)

        setContent{
            TIP102Group01FriendzyTheme{
                val startDestination = if (memberToken != null){
                    Screen.TabMainScreen.name
                }else{
                    Screen.LoginScreen.name
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Main(startDestination = startDestination)
                }
            }
        }
//        setContent {
//            TIP102Group01FriendzyTheme {
//                Main()
//            }
//        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MainPreview() {
//    TIP102Group01FriendzyTheme {
//        Main()
//    }
//}