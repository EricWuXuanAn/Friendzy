package com.example.tip102group01friendzy.ui.feature.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SearchWithMap()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithMap() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        bottomBar = { BottomNavigationBar() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Address and Search Bar in the same row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Address input takes most of the width
                    val barHeight = 56.dp // Set a consistent height for both bars

                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Browsing from this address") },
                        placeholder = { Text("e.g., 1234 Elm Street") },
                        singleLine = true,
                        shape = RoundedCornerShape(50.dp), // Set rounded corners
                        modifier = Modifier
                            .weight(0.8f)
                            .height(barHeight), // Set height
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF4EAF5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Spacer between address and search bar

                    // Search Bar takes 20% of the width
                    TextField(
                        singleLine = true,
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search", color = Color.Gray, fontSize = 15.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.text.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = TextFieldValue("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF4EAF5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(50.dp), // Set rounded corners
                        modifier = Modifier
                            .weight(0.2f)
                            .height(barHeight) // Match the height of Address Bar
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Map container that fills the remaining space
                MapViewContainer(modifier = Modifier.weight(1f))
            }
        }
    )
}

@Composable
fun MapViewContainer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        // 使用 Coil 加載網路圖片
        val imageUrl = "https://mono.software/2017/12/29/google-maps-marker-clustering/initial_clustering.jpg"
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Google Map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Nearby Buddy") },
            label = { Text("地圖搜尋") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite") },
            label = { Text("收藏") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Onboard services") },
            label = { Text("刊登") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Chat") },
            label = { Text("聊聊") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
            label = { Text("個人檔案") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchWithMapPreview() {
    MaterialTheme {
        SearchWithMap()
    }
}
