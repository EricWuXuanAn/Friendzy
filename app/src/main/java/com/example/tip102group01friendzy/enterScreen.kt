package com.example.tip102group01friendzy

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme
import kotlinx.coroutines.delay

@Composable
fun EnterScreen(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000)
    )

    LaunchedEffect(Unit) {
        isVisible = true
        delay(1000)
        navController.navigate(Screen.LoginScreen.name){
            popUpTo(Screen.EnterScreen.name){inclusive=true}
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.28f)
        ) {
            Image(
                painter = painterResource(R.drawable.puzzles),
                contentDescription = "puzzle",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(R.drawable.title),
                contentDescription = "lightning",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomEnd)
                    .graphicsLayer(alpha = alpha)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EnterScreenPreview() {
    TIP102Group01FriendzyTheme {
        EnterScreen(rememberNavController())
    }
}