package com.begunok.begunok.screens.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.begunok.begunok.ui.theme.MainCl

@Composable
fun BottomBarUI(
    navigationController: NavHostController,
    screenItemsBar: List<Screens>,
    bottomBarState: MutableState<Boolean>
) {
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar(containerColor = MainCl) {
                screenItemsBar.forEach { screens ->
                    if (screens.showIconOnBottomBar) {
                        NavigationBarItem(
                            selected = currentDestination?.route == screens.screen,
                            onClick = {
                                navigationController.navigate(screens.screen) {
                                    launchSingleTop = true
                                    popUpTo(navigationController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(screens.iconRes),
                                    contentDescription = stringResource(screens.titleRes)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(screens.titleRes)
                                )
                            },
                            alwaysShowLabel = false
                        )
                    }
                }
            }
        }
    )
        /*IconButton(onClick = {
            selected.value = Icons.Default.AddCircle
            navigationController.navigate(Screens.Devices.screen) {
                //popUpTo(0)
            }
        }, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = if (selected.value == Icons.Default.AddCircle) Color.White else Color.DarkGray
            )
        }
        IconButton(onClick = {
            selected.value = Icons.Default.Add
            navigationController.navigate(Screens.Connect.screen) {
                //popUpTo(0)
            }
        }, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = if (selected.value == Icons.Default.Add) Color.White else Color.DarkGray
            )
        }

        IconButton(onClick = {
            selected.value = Icons.Default.Place
            navigationController.navigate(Screens.Map.screen) {
                //popUpTo(0)
            }
        }, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.Default.Place,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = if (selected.value == Icons.Default.Place) Color.White else Color.DarkGray
            )
        }*/
}
