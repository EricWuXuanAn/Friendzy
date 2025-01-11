package com.example.tip102group01friendzy.ui.feature.search

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var showAllInfoWindows by remember { mutableStateOf(false) }

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
            onMemberSelected = { navController.navigate(Screen.MemberScreen.name) },
            isMyLocationEnabled = isLocationPermissionGranted,
            defaultLocation = defaultLocation,
            companions = getMockCompanions(),
            showAllInfoWindows = showAllInfoWindows
        )
    }
}

@Composable
fun LocationRequestDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("開定位尋找附近陪伴者") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.peoplenearby), contentDescription = "Reference Image", modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "允許我們存取您的位置，以提供更好的陪伴者推薦。", modifier = Modifier.padding(8.dp), fontSize = 16.sp
                )
            }
        },
        confirmButton = {
            Text(
                text = "好", modifier = Modifier.clickable { onConfirm() }.padding(8.dp), color = Color.DarkGray, fontSize = 18.sp
            )
        },
        dismissButton = {
            Text(
                text = "不用", modifier = Modifier.clickable { onDismiss() }.padding(8.dp), color = Color.Gray, fontSize = 18.sp
            )
        }
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
        if (showInfoWindow) {
            markerState.showInfoWindow() // 顯示 InfoWindow
        }
    }

    // 添加 Marker
    Marker(
        state = markerState,
        title = companion.nickname ?: "Unknown", // 如果 nickname 為 null，顯示 "Unknown"
        snippet = companion.description ?: "No description available", // 如果 description 為 null，顯示默認值
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE), // Marker 顏色
        onClick = {
            onMemberSelected(companion) // 點擊時觸發回調
            false // 返回 false 以顯示 InfoWindow
        }
    )
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