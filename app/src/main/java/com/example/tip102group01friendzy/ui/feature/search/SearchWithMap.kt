package com.example.tip102group01friendzy.ui.feature.search

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavigationComponent(navController)
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "SearchWithMap") {
        composable("SearchWithMap") { SearchWithMap(navController) }
        composable("SearchResult") { SearchResult() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithMap(navController: NavHostController) {
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
                    val barHeight = 56.dp // Set a consistent height for both bars

                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("瀏覽地址周邊的夥伴") },
                        placeholder = { Text("例如, 台北市信義區忠孝東路四段......") },
                        singleLine = true,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .weight(0.8f)
                            .height(barHeight),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF4EAF5),
                            unfocusedContainerColor = Color(0xFFF4EAF5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF4EAF5),
                            unfocusedContainerColor = Color(0xFFF4EAF5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .weight(0.2f)
                            .height(barHeight)
                            .background(Color(0xFFF4EAF5))
                            .padding(8.dp)
                            .then(Modifier.padding(end = 16.dp)) // Additional padding
                            .clickable { navController.navigate("SearchResult") } // Navigate to SearchResult
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Map container that fills the remaining space
                MapViewContainer(
                    modifier = Modifier.weight(15f),
                    buddyMap = { BuddyMap() } // 傳遞 BuddyMap 作為參數
                )
            }
        }
    )
}

@Composable
fun SearchResult() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Search Result Screen", fontSize = 20.sp)
    }
}

@Composable
fun MapViewContainer(modifier: Modifier = Modifier, buddyMap: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        // 在 Box 的上層疊加 Buddy Map
        buddyMap()
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
            label = { Text("搜附近") }
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BuddyMap() {
    // 獲取當前的 Context，用於 Toast 或其他與系統互動的操作
    val context = LocalContext.current

    // 目標位置的緯度與經度（台北市一個示例位置）
    val target = LatLng(25.033964, 121.564468)

    // 使用 rememberCameraPositionState 記住相機位置狀態
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(target, 17f) // 設定目標座標和縮放級別
    }

    // 定義位置權限的狀態管理
    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION // 定位權限
    )

    // 在組件掛載時請求位置權限
    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest() // 如果尚未授予，則請求權限
        }
    }

    // 使用 GoogleMap API 顯示地圖
    GoogleMap(
        modifier = Modifier.fillMaxSize(), // 地圖填滿整個容器
        cameraPositionState = cameraPositionState, // 綁定相機位置狀態
        properties = MapProperties(
            isMyLocationEnabled = locationPermission.status.isGranted, // 根據權限啟用定位
            isTrafficEnabled = true, // 顯示即時交通資訊
            mapType = MapType.NORMAL, // 地圖類型為標準地圖
            latLngBoundsForCameraTarget = null // 不設置邊界限制
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true, // 啟用指南針
            myLocationButtonEnabled = locationPermission.status.isGranted, // 啟用我的位置按鈕
            rotationGesturesEnabled = true, // 啟用旋轉手勢
            scrollGesturesEnabled = true, // 啟用滾動手勢
            scrollGesturesEnabledDuringRotateOrZoom = true, // 啟用滾動時的縮放或旋轉
            tiltGesturesEnabled = true, // 啟用傾斜手勢
            zoomControlsEnabled = true, // 啟用縮放控制
            zoomGesturesEnabled = true // 啟用縮放手勢
        ),
        onMapLoaded = {
            // 地圖載入完成後顯示提示訊息
            Toast.makeText(context, "Map Loaded", Toast.LENGTH_SHORT).show()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BuddyMapPreview() {
    // 在預覽中顯示 Google Map（無需位置權限）
    GoogleMap(
        modifier = Modifier.fillMaxSize(), // 填滿容器
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(25.0338352, 121.5644995), 15f) // 台北 101
        },
        properties = MapProperties(
            isMyLocationEnabled = false, // 跳過位置權限
            mapType = MapType.NORMAL // 使用標準地圖類型
        ),
        uiSettings = MapUiSettings(
            compassEnabled = false, // 關閉指南針
            myLocationButtonEnabled = true // 啟用我的位置按鈕
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchWithMapPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        NavigationComponent(navController)
    }
}
