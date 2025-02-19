package com.begunok.begunok.screens.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.begunok.begunok.R
import com.begunok.begunok.data.db.MainDb
import com.begunok.begunok.data.models.BLEItem
import com.begunok.begunok.screens.menu.Screens

@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview
@Composable
fun DevicesUI(
    pav: PaddingValues,
    bleItemsDb: MainDb,
    navigationController: NavHostController
) {

    val bleItemStateList = bleItemsDb.dao().getAllBLEItems()
        .collectAsState(initial = emptyList())
    val bleItemMain = bleItemStateList.value.findLast { it.main }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = pav.calculateTopPadding(), bottom = pav.calculateBottomPadding())
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (bleItemMain != null) {
                    BleItemCard(bleItemMain, navigationController)
                } else {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.begunok_logo),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Нет главного устройства",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Gray // Можно изменить цвет текста
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(bleItemStateList.value) { bleItem ->
                    if (!bleItem.main) {
                        BleItemCard(bleItem, navigationController)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }
        }
    }
}

@Composable
fun BleItemCard(
    bleItem: BLEItem,
    navigationController: NavHostController
) {
    val padding = 10.dp

    Card(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        //backgroundColor = Color.Gray,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.begunok_logo),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    Text(
                        text = bleItem.localName + " ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (bleItem.main) {
                        Text(
                            text = stringResource(R.string.you),
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else if (bleItem.model != null) {
                        Text(
                            text = "(${bleItem.model})",
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                // Getting last distance and time
                val lastInfo = bleItem.timeDistanceList.lastOrNull()
                if (bleItem.main) {
                    Text(text = "Подключено")
                }
                else if (lastInfo != null) {
                    Text(text = "Последняя дистанция: ${lastInfo.distance}")
                    Text(text = "Последнее время обновления: ${lastInfo.timeLast}")
                } else {
                    Text(text = "Соединений не было" + bleItem.id)
                }
            }

            // Connect main device
            if (bleItem.main) {
                IconButton(onClick = { navigationController.navigateUp() }) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "Back"
                    )
                }
            }

            // Device settings
            IconButton(onClick = {
                navigationController.navigate(Screens.ChangeBLEItem.screen + "/" + bleItem.id.toString()) {
                    launchSingleTop = true
                    popUpTo(navigationController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dashboard_black_24dp),
                    contentDescription = "Настройки"
                )
            }
        }
    }
}
