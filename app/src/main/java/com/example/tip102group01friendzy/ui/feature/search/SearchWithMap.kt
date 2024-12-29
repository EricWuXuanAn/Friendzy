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
import com.example.tip102group01friendzy.R
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

// 預設導航組件，用於控制不同頁面的切換
@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "SearchWithMap") {
        composable("SearchWithMap") { SearchWithMap(navController) }
        composable("SearchResult") { SearchResult() }
    }
}

// 搜索與地圖頁面的主要布局
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithMap(navController: NavHostController) {
    val turnOff:Boolean = false // 控制底部導航條是否顯示
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) } // 搜索欄的文字狀態
    var address by remember { mutableStateOf(TextFieldValue("")) } // 地址欄的文字狀態

    Scaffold(
        bottomBar = { if(turnOff) {
            BottomNavigationBar()
        } },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // 地址與搜索欄同排顯示
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val barHeight = 56.dp // 統一設定搜索欄與地址欄的高度

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
                            .then(Modifier.padding(end = 16.dp))
                            .clickable { navController.navigate("SearchResult") } // 點擊跳轉到搜索結果頁面
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 地圖容器，填充剩餘空間
                MapViewContainer(
                    modifier = Modifier.weight(15f),
                    buddyMap = { BuddyMap() } // 傳遞 BuddyMap 作為參數
                )
            }
        }
    )
}

// 搜索結果頁面
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

// 地圖外框容器
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

// 底部導航欄
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

// 顯示 Buddy Map
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BuddyMap() {
    val context = LocalContext.current // 獲取當前的 Context
    val target = LatLng(25.033964, 121.564468) // 台北市示例位置
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(target, 17f)
    }

    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
    }

    val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_orange)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = locationPermission.status.isGranted,
            isTrafficEnabled = false,
            mapType = MapType.NORMAL,
            mapStyleOptions = mapStyleOptions
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true,
            myLocationButtonEnabled = locationPermission.status.isGranted,
            rotationGesturesEnabled = true,
            scrollGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,
            tiltGesturesEnabled = true,
            zoomControlsEnabled = true,
            zoomGesturesEnabled = true
        ),
        onMapLoaded = {
            Toast.makeText(context, "Map Loaded", Toast.LENGTH_SHORT).show()
        }
    )
}

// Buddy Map 預覽
@Preview(showBackground = true)
@Composable
fun BuddyMapPreview() {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(25.0338352, 121.5644995), 15f)
        },
        properties = MapProperties(
            isMyLocationEnabled = false,
            mapType = MapType.NORMAL
        ),
        uiSettings = MapUiSettings(
            compassEnabled = false,
            myLocationButtonEnabled = true
        )
    )
}

// 搜索地圖頁面預覽
@Preview(showBackground = true)
@Composable
fun SearchWithMapPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        NavigationComponent(navController)
    }
}
