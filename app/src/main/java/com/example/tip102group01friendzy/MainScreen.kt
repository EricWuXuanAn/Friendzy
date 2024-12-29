package com.example.tip102group01friendzy

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.ui.feature.Memberpage.MemberScreen
import com.example.tip102group01friendzy.ui.feature.Memberpage.Settingpage
import com.example.tip102group01friendzy.ui.feature.account.ForgetPasswordScreen
import com.example.tip102group01friendzy.ui.feature.account.ForgetPasswordViewModel
import com.example.tip102group01friendzy.ui.feature.account.LoginScreen
import com.example.tip102group01friendzy.ui.feature.account.LoginViewModel
import com.example.tip102group01friendzy.ui.feature.account.RegisterScreen
import com.example.tip102group01friendzy.ui.feature.account.RegisterViewModel
import com.example.tip102group01friendzy.ui.feature.chat.ChatroomScreen
import com.example.tip102group01friendzy.ui.feature.customer.CustomerScreen
import com.example.tip102group01friendzy.ui.feature.customer.CustomerVM
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_and_BkackListScreen
import com.example.tip102group01friendzy.ui.feature.customer.Favorite_and_Black_ListVM
import com.example.tip102group01friendzy.ui.feature.customer.OrderListScreen
import com.example.tip102group01friendzy.ui.feature.customer.OrderVM
import com.example.tip102group01friendzy.ui.feature.customer.PostListVM
import com.example.tip102group01friendzy.ui.feature.customer.PostScreen
import com.example.tip102group01friendzy.ui.feature.customer.PostVM

import com.example.tip102group01friendzy.ui.feature.customer.ReservationVM
import com.example.tip102group01friendzy.ui.feature.search.SearchWithMap
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme

enum class Screen(@StringRes val title: Int) {
    LoginScreen(title = R.string.LoginScreen),
    RegisterScreen(title = R.string.RegisterScreen),
    ForgetPasswordScreen(title = R.string.ForgetPasswordScreen),

    ChatroomScreen(title = R.string.ChatroomScreen),
    SearchResultScreen(title = R.string.SearchResultScreen),
    SearchWithMapScreen(title = R.string.SearchWithMapRScreen),
    OrderScreen(title = R.string.OrderScreen),
    Favorite_and_BlackListScreen(title = R.string.Favorite_and_BlackListScreen),
    ReservationScreen(title = R.string.reservationScreen),
    SettingScreen(title = R.string.Setting),
    MemberScreen(title = R.string.Member),
    CustomerScreen(title = R.string.CustomerScreen),
    EnterScreen(title = R.string.enterScreen),
    ReservationConfirmScreen(title = R.string.reservationConfirmScreen),
    PostScreen(title = R.string.post),
    NoticeScreen(title = R.string.NoticeScreen),
}
/**
 * Main是一個頁面容器，其他頁面會依照使用者操作被加上來
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    //導覽式頁面控制器
    navController: NavHostController = rememberNavController(),
    customerVM: CustomerVM = CustomerVM(),
    orderlistVM: OrderVM = OrderVM(),
    reservationVM: ReservationVM = ReservationVM(),
    favorite_and_bkacklistVM: Favorite_and_Black_ListVM = Favorite_and_Black_ListVM(),
    postVM: PostVM = PostVM(),
    postListVM: PostListVM = PostListVM(),
    tabVM: TabVM = TabVM()
) {
    // 取得儲存在back stack最上層的頁面 //BackStack:儲存歷史資料的容器
    val backStackEntry by navController.currentBackStackEntryAsState()
    // 取得當前頁面的名稱
    val currentScreen = Screen.valueOf(
        /* route是目前路徑，例如：Screen02 (代表要去Screen02頁面)。
            若要攜帶參數到下頁則為：Screen02/john (john是參數對應的值)，
            此時需要呼叫split().first()取得頁面名稱方便enum Screen比對。
            若為空值則回傳Screen01 */
        //destination最新物件 > route 最新物件的路徑
        backStackEntry?.destination?.route?.split("/")?.first() ?: Screen.LoginScreen.name
    )
    // 設定內容向上捲動時，TopAppBar自動收起來；呼叫pinnedScrollBehavior()則不會收起來
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        // 設定則可追蹤捲動狀態，藉此調整畫面(例如內容向上捲動時，TopAppBar自動收起來)
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainAppBar(
                currentScreen = currentScreen,
                //控制有沒有前一頁的箭頭
                canNavigateBack = navController.previousBackStackEntry != null,
                /* navigateUp()與popBackStack()都可回前頁，但差別是否從其他app來此app首頁：
                   navigateUp()：可以回到來源app，較適合用於左上角的"Up"按鈕
                   popBackStack()：只能單純回到前頁，而無法回到來源app */
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.LoginScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            composable(route = Screen.LoginScreen.name) {
                LoginScreen(
                    navController = navController,
                    loginViewModel = LoginViewModel()
                )
            }
            composable(
                route = Screen.RegisterScreen.name
            ) {
                RegisterScreen(
                    navController = navController,
                    registerViewModel = RegisterViewModel()
                )
            }
            composable(
                route = Screen.ForgetPasswordScreen.name
            ) { backStackEntry ->
                ForgetPasswordScreen(
                    navController = navController,
                    forgetPasswordViewModel = ForgetPasswordViewModel()
                )
            }
            composable(
                route = Screen.OrderScreen.name
            ) {
                OrderListScreen(navController = navController, orderlistVM = orderlistVM)
            }
            composable(
                route = Screen.CustomerScreen.name
            ) {
                CustomerScreen(navController = navController, customerVM = customerVM, postListVM = postListVM)
            }
            composable(
                route = Screen.Favorite_and_BlackListScreen.name
            ) {
                Favorite_and_BkackListScreen(
                    navController = navController,
                    favorite_and_bkacklistVM = favorite_and_bkacklistVM
                )
            }
//            composable(
//                route = Screen.Reservation.name
//            ) {
//                ReservationScreen(navController = navController, reservationVM = reservationVM, PostListVM())
//            }
            composable(
                route = Screen.ChatroomScreen.name
            ) { backStackEntry ->
                ChatroomScreen(
                    navController = navController,
                    tabVM = tabVM
                )
            }
            composable(
                route = Screen.EnterScreen.name
            ) {
                EnterScreen(
                    navController = navController, tabVM = tabVM
                )
            }
            composable(route = Screen.SettingScreen.name) {
                Settingpage(navController = navController, settingVM = viewModel())
            }

            composable(route = Screen.MemberScreen.name) {
                MemberScreen(navController = navController, memberVM = viewModel())
            }
            composable(route = Screen.PostScreen.name){
                PostScreen(navController = navController, postVM = postVM)
            }

            composable(route = Screen.SearchWithMapScreen.name) {
                SearchWithMap(navController = navController)
            }

            composable(route = Screen.NoticeScreen.name) {
                SearchWithMap(navController = navController)
            }


        }
    }
}

/**
 * 建立topBar與回前頁按鈕
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
//         設定頁面標題
        title = { Text(text = "") },
        modifier = modifier,
        navigationIcon = {
            // 如果可回前頁，就顯示Back按鈕
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TIP102Group01FriendzyTheme {
//        Main()
    }
}