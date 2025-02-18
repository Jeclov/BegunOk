package com.begunok.begunok.add.item.fast

import android.util.Log
import android.widget.Toast
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.begunok.begunok.MainActivity
import com.begunok.begunok.R
import com.begunok.begunok.data.db.MainDb
import com.begunok.begunok.data.models.BLEItem
import com.begunok.begunok.data.parsers.parseDeviceJson
import com.begunok.begunok.screens.menu.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun saveDevice(contents: String, bleItemsDb: MainDb, context: MainActivity) {
    val bleItem = parseDeviceJson(contents)
    if (bleItem == null) {
        Log.d("TestLog", "Scan non-BegunOk device")
        Toast.makeText(
            context,
            context.getString(R.string.qr_non_begunok_device), Toast.LENGTH_SHORT
        )
            .show()
    } else {
        var bleItemNonCopy: BLEItem?
        CoroutineScope(Dispatchers.IO).launch {
            bleItemNonCopy = bleItemsDb.dao().getBLEItemByMac(bleItem.mac)

            val address: String
            if (bleItemNonCopy == null) {
                bleItemsDb.dao().insertBLEItem(bleItem)
                Log.d("TestLog", "Item is saved from fast")
                address = "/"
            } else {
                val updateBLEItem = BLEItem(
                    id = bleItemNonCopy!!.id,
                    idGlobal = bleItem.idGlobal,
                    localName = bleItemNonCopy!!.localName,
                    mac = bleItemNonCopy!!.mac,
                    model = bleItem.model,
                    timeDistanceList = bleItemNonCopy!!.timeDistanceList,
                    changerHistoryList = bleItemNonCopy!!.changerHistoryList,
                    password = bleItemNonCopy!!.password,
                    main = bleItemNonCopy!!.main,
                )
                bleItemsDb.dao().updateBLEItem(updateBLEItem)
                context.runOnUiThread {
                    Toast.makeText(
                        context,
                        context.getString(R.string.it_is_exist_already), Toast.LENGTH_SHORT
                    )
                        .show()
                }
                address = "/" + bleItemNonCopy!!.id.toString()
            }
            context.runOnUiThread {
                context.navigationController.navigate(Screens.ChangeBLEItem.screen + address) {
                    launchSingleTop = true
                    popUpTo(context.navigationController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                }
            }
        }
    }
}