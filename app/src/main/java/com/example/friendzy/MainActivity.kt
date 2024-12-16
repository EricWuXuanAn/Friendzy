package com.example.friendzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.friendzy.ui.customer.CustomerVM
import com.example.friendzy.ui.theme.FriendzyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FriendzyTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main(){}



@Composable
fun FriendzyNavHost(
    navController: NavHostController = rememberNavController()
){

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FriendzyTheme {
        Main()
    }
}