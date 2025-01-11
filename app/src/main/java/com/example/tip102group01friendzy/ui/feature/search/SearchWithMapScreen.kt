package com.example.tip102group01friendzy.ui.feature.search
import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
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
    cameraPositionState: CompanionInfo
) {
    var showPopupState by remember { mutableStateOf(showPopup) }
    var showAllInfoWindows by remember { mutableStateOf(false) }
//    var searchText by remember { mutableStateOf("") }

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
                showAllInfoWindows = true
            },
            onConfirm = {
                showPopupState = false
                showAllInfoWindows = true
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

        // 添加懸浮的搜尋欄
//        TextField(
//            value = searchText,
//            onValueChange = { searchText = it },
//            placeholder = { Text("搜尋地點或陪伴者") },
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(16.dp)
//                .fillMaxWidth(0.9f), // 可調整搜尋欄的寬度
//            shape = RoundedCornerShape(34.dp), // 設定圓角
//            colors = TextFieldDefaults.colors(),
//            singleLine = true
//        )
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

    Marker(
        state = markerState,
        title = companion.name,
//      snippet = companion.description,
        snippet = "${companion.name}\n${companion.description}", // 添加 name 和 description
        icon = BitmapDescriptorFactory.defaultMarker(30f),
        onClick = {
            onMemberSelected(companion)
            true // 確保不干擾 InfoWindow 顯示
        }
    )

    if (showInfoWindow) {
        LaunchedEffect(Unit) {
            markerState.showInfoWindow() // 顯示 Marker 的 InfoWindow
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
            LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3)
    )
}