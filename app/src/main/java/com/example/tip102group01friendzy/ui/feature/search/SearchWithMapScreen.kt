package com.example.tip102group01friendzy.ui.feature.search

import android.Manifest
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchWithMapScreen(
    navController: NavHostController,
    defaultLocation: LatLng = LatLng(25.0330, 121.5654),
    showPopup: Boolean = true,
    onMemberSelected: (String) -> Unit,
    cameraPositionState: CompanionInfo,
    tabVM: TabVM
) {
    var showPopupState by remember { mutableStateOf(showPopup) }
    var showAllInfoWindows by remember { mutableStateOf(true) }

    val fineLocationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseLocationPermission = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

    val isLocationPermissionGranted = fineLocationPermission.status.isGranted && coarseLocationPermission.status.isGranted

    LaunchedEffect(fineLocationPermission.status.isGranted, coarseLocationPermission.status.isGranted) {
        if (!fineLocationPermission.status.isGranted) fineLocationPermission.launchPermissionRequest()
        if (!coarseLocationPermission.status.isGranted) coarseLocationPermission.launchPermissionRequest()
    }

    if (showPopupState && !isLocationPermissionGranted) {
        LocationRequestDialog(
            onDismiss = {
                showPopupState = false
                showAllInfoWindows = true // 顯示所有 Marker 的 InfoWindow
            },
            onConfirm = {
                showPopupState = false
                showAllInfoWindows = true // 顯示所有 Marker 的 InfoWindow
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BuddyMap(
            onMemberSelected = { navController.navigate(Screen.ForothersScreen.name) },
            isMyLocationEnabled = isLocationPermissionGranted,
            defaultLocation = defaultLocation,
            companions = getMockCompanions(),
            showAllInfoWindows = showAllInfoWindows
        )

        // 在地圖上方顯示浮動搜尋欄
        FloatingSearchBar()
    }
}

@Composable
fun LocationRequestDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Text(
                text = "好",
                modifier = Modifier
                    .clickable { onConfirm() }
                    .padding(8.dp),
                color = Color.Blue
            )
        },
        dismissButton = {
            Text(
                text = "不用",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp),
                color = Color.Gray
            )
        },
        title = {
            Text(text = "開定位尋找附近陪伴者")
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.peoplenearby),
                    contentDescription = "Cute Image",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "允許我們存取您的位置，以提供更好的陪伴者推薦。",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp
                )
            }
        },
    )
}

@Composable
fun BuddyMap(
    onMemberSelected: (CompanionInfo) -> Unit,
    isMyLocationEnabled: Boolean,
    defaultLocation: LatLng,
    companions: List<CompanionInfo>,
    showAllInfoWindows: Boolean
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = isMyLocationEnabled,
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_friendzy)
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true,
            myLocationButtonEnabled = true,
            zoomControlsEnabled = true
        )

    ) {
        companions.forEach { companion ->
            UserMarker(
                companion = companion,
                onMemberSelected = onMemberSelected,
                showInfoWindow = showAllInfoWindows
            )
        }
    }
}

@Composable
fun UserMarker(
    companion: CompanionInfo,
    onMemberSelected: (CompanionInfo) -> Unit,
    showInfoWindow: Boolean
) {
    val markerState = rememberMarkerState(position = companion.location)

    // 強制顯示 InfoWindow
    LaunchedEffect(showInfoWindow) {
        Log.d("InfoWindow", "Marker Position: ${markerState.position}")
        if (showInfoWindow) {
            markerState.showInfoWindow()
            Log.d("InfoWindow", "showInfoWindow called")
        }
    }

    // 客製化可愛的map pin，如 "cute_marker.png"
//    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cute_marker)
//    val customIcon = BitmapDescriptorFactory.fromBitmap(bitmap)

    // 添加 Marker
    Marker(
        state = markerState,
        title = companion.nickname ?: "Unknown", // 如果 nickname 為 null，顯示 "Unknown"
        snippet = companion.description ?: "No description available", // 如果 description 為 null，顯示默認值
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE), // Marker 顏色
        onClick = {
            markerState.showInfoWindow()
            onMemberSelected(companion) // 點擊時觸發回調
            false // 返回 false 以顯示 InfoWindow
        }
    )
}

@Composable
fun FloatingSearchBar() {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        // 搜索框
        Row(
            Modifier
                .padding(horizontal = 22.dp) // 左右间距
                .padding(top = 35.dp) // 下移xdp
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White.copy(alpha = 0.9f)) // 半透明背景
//                .background(Color.White) // 不透明白色背景
                .height(60.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 插圖
            Box(
                Modifier
                    .padding(start = 16.dp)
                    .size(30.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.LightGray.copy(alpha = 0.6f)), // 半透明灰底
                contentAlignment = Alignment.Center
            ) {
                // 搜索圖標
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_search), // 預設資源
                    contentDescription = "Cute Icon",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Gray) // 插圖顏色可調整
                )
            }

            // 搜索文字
            Text(
                text = "Helper, part-time in taipei",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchWithMapScreenPreview() {
    val navController = rememberNavController()
    val defaultLocation = LatLng(25.0330, 121.5654)

    SearchWithMapScreen(
        navController = navController,
        defaultLocation = defaultLocation,
        onMemberSelected = { memberName -> println("Selected: $memberName") },
        cameraPositionState = CompanionInfo("1", "Nita", "搬家&油漆幫手", "信義區",
            LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3 ),
        tabVM = TabVM()
    )
}