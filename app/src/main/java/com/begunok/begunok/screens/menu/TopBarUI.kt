package com.begunok.begunok.screens.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.begunok.begunok.R
import com.begunok.begunok.ui.theme.MainCl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUI(
    navigationController: NavHostController,
    topBarState: MutableState<Boolean>,
) {
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainCl,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navigationController.navigate(Screens.Settings.screen) {
                            launchSingleTop = true
                            popUpTo(navigationController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    }) { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
                }
            )
        }
    )
}