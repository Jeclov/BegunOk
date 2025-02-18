package com.begunok.begunok.screens.menu

import android.annotation.SuppressLint
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.begunok.begunok.MainActivity
import com.begunok.begunok.data.db.MainDb
import com.begunok.begunok.screens.connect.ConnectUI
import com.begunok.begunok.screens.change.ChangeDeviceParameters
import com.begunok.begunok.screens.devices.DevicesUI
import com.begunok.begunok.screens.map.MapUI
import com.begunok.begunok.screens.settings.SettingsUI
import com.journeyapps.barcodescanner.ScanOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavMenuUI(
    navigationController: NavHostController,
    bleItemsDb: MainDb,
    context: MainActivity,
    scanLauncher: ActivityResultLauncher<ScanOptions>
) {

    val screenItemsBar = listOf(
        Screens.Devices,
        Screens.Connect,
        Screens.Map,
        Screens.Settings,
        Screens.ChangeBLEItem
    )

    // State of topBar, set state to false, if current page showTopBar = false
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    // State of bottomBar, set state to false, if current page showBottomBar = false
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    screenItemsBar.forEach { screens ->
        val realRoute = navBackStackEntry?.destination?.route
        val index = realRoute?.indexOf("/")
        val route =
            if (index == -1) realRoute else navBackStackEntry?.destination?.route?.substring(
                0,
                index!!
            )
        if (route == screens.screen) {
            topBarState.value = screens.showTopBar
            bottomBarState.value = screens.showBottomBar
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarUI(navigationController, topBarState)
        },
        bottomBar = {
            BottomBarUI(navigationController, screenItemsBar, bottomBarState)
        }
    ) { paddingValues -> // padding of top bar and bottom bar
        NavHost(
            navController = navigationController, startDestination = Screens.Connect.screen
        ) {
            composable(Screens.Devices.screen) {
                DevicesUI(
                    pav = paddingValues,
                    navigationController = navigationController,
                    bleItemsDb = bleItemsDb,
                )
            }
            composable(Screens.Connect.screen) {
                ConnectUI(
                    pav = paddingValues,
                    bleItemsDb = bleItemsDb,
                    context = context,
                    scanLauncher
                )
            }
            composable(Screens.Map.screen) { MapUI(pav = paddingValues) }
            composable(Screens.Settings.screen) { SettingsUI(navigationController = navigationController) }
            composable(Screens.ChangeBLEItem.screen + "/{id}") {
                val id = it.arguments?.getString("id")?.toIntOrNull()
                ChangeDeviceParameters(
                    id,
                    navigationController = navigationController,
                    bleItemsDb = bleItemsDb,
                    context = context
                )
            }
        }
    }
}

