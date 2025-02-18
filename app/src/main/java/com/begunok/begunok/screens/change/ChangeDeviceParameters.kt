package com.begunok.begunok.screens.change

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.begunok.begunok.R
import com.begunok.begunok.data.db.MainDb
import com.begunok.begunok.data.models.BLEItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChangeDeviceParameters(
    id: Int?,
    bleItemsDb: MainDb,
    context: Context,
    navigationController: NavHostController
) {

    val bleItemsDbDialog = bleItemsDb.dao()
    val bleItemStateList = bleItemsDb.dao().getAllBLEItems()
        .collectAsState(initial = emptyList())
    val showAlertLogOut = remember {
        mutableStateOf(false)
    }
    if (showAlertLogOut.value) {
        AlertLogOut(navigationController, showAlertLogOut)
    }
    val scrollState = rememberScrollState()

//    val last = bleItemStateList.value

    if (bleItemStateList.value.isEmpty()) return

    val idBleLocal: Int = id ?: bleItemStateList.value.last().id!!
    var bleItem: BLEItem? = null

    for (ble in bleItemStateList.value) {
        if (ble.id == idBleLocal) {
            bleItem = ble
        }
    }

    if (bleItem == null && idBleLocal > 0) {
        Toast.makeText(context, "Error BegunOk device not found", Toast.LENGTH_SHORT).show()
        //logout(dialogState)
    } else if (idBleLocal <= 0) {
        Toast.makeText(context, "Database research", Toast.LENGTH_SHORT).show()
    } else {
        //Toast.makeText(context, "BegunOk device found!", Toast.LENGTH_SHORT).show()
    }
    val dialogTextName: MutableState<String>
    val dialogTextPassword: MutableState<String>
    val checkedState: MutableState<Boolean>
    val defaultName = stringResource(R.string.app_name)


    if (bleItem != null) {
        dialogTextName = remember { mutableStateOf(bleItem.localName) }
        dialogTextPassword = remember { mutableStateOf(bleItem.password) }
        checkedState = remember { mutableStateOf(bleItem.main) }
    } else {
        dialogTextName = remember { mutableStateOf("") }
        dialogTextPassword = remember { mutableStateOf("") }
        checkedState = remember { mutableStateOf(false) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                //.background(MainCl)
                .fillMaxWidth()
                .statusBarsPadding()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navigationController.navigateUp() },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                IconButton(
                    onClick = {
                        bleItem?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                bleItemsDbDialog.deleteBLEItem(it)
                            }
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                            showAlertLogOut.value = true
                        }
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "Delete"
                    )
                }
            }

            // Title of device change menu
            Text(
                text = "Настройки устройства",
                style = MaterialTheme.typography.titleLarge,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 16.dp, start = 5.dp, end = 5.dp)
            )

            // Gap to change local name
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Введите имя устройства",
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                TextField(
                    value = dialogTextName.value,
                    onValueChange = { dialogTextName.value = it },
                    label = {
                        Row {
                            Text(
                                text = "Имя устройства",
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "(опционально)" + id,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray
                            )
                        }
                    }
                )
            }

            // Gap to change password
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Введите пароль",
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                TextField(
                    value = dialogTextPassword.value,
                    onValueChange = { dialogTextPassword.value = it },
                    label = {
                        Row {
                            Text(
                                text = "Пароль",
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "(опционально)" + idBleLocal,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray
                            )
                        }
                    }
                )
            }

            // Gap with checkbox to maid device main
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Box(modifier = Modifier.weight(5f))
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Right,
                    text = "Назначить главным утройством?",
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    modifier = Modifier,
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
                Box(modifier = Modifier.weight(5f))
            }
            if (bleItem != null) {
                Text(text = bleItem.mac + " " + bleItem.idGlobal + " " + bleItem.model + " ")
            }

            // Cancel and Ok buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        showAlertLogOut.value = true
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(text = stringResource(if(id != null)R.string.cancel else R.string.default_value_device))
                }
                Button(
                    onClick = {
                        bleItem?.let {
                            it.localName = dialogTextName.value.ifBlank {
                                defaultName
                            }
                            it.password = dialogTextPassword.value
                            it.main = checkedState.value

                            CoroutineScope(Dispatchers.IO).launch {
                                bleItemsDbDialog.updateBLEItem(it)
                            }

                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()

                            if (it.main) {
                                bleItemStateList.value.forEach { item ->
                                    if (item != it) {
                                        item.main = false
                                        CoroutineScope(Dispatchers.IO).launch {
                                            bleItemsDbDialog.updateBLEItem(item)
                                        }
                                    }
                                }
                                Toast.makeText(context, "Change main", Toast.LENGTH_SHORT).show()
                            }
                        } ?: run {
                            Toast.makeText(context, "Что?", Toast.LENGTH_SHORT).show()
                        }
                        onDismiss(navigationController)
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            }
        }
    }
}

fun onDismiss(navigationController: NavHostController) {
    navigationController.navigateUp()
}

@Composable
fun AlertLogOut(
    navigationController: NavHostController,
    showAlertLogOut: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss(navigationController)
                showAlertLogOut.value = false
            }) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {

            TextButton(onClick = {
                showAlertLogOut.value = false
            }
            ) {
                Text(text = stringResource(R.string.cancel))
            }

        },
        title = {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "Delete"
                )
                Text(
                    text = "Вы уверены?",
                    modifier = Modifier.weight(2f)
                )
            }
        }
    )
}
