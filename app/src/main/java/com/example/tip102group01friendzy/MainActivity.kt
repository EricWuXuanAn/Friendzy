package com.example.tip102group01friendzy

import android.annotation.SuppressLint
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TIP102Group01FriendzyTheme {
                Main()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    TIP102Group01FriendzyTheme {
        Main()
    }
}