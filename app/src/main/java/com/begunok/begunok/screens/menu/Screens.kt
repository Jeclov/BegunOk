package com.begunok.begunok.screens.menu

import androidx.annotation.DrawableRes
import com.begunok.begunok.R

sealed class Screens(
    val titleRes: Int,
    val screen: String,
    @DrawableRes val iconRes: Int,
    val showIconOnBottomBar: Boolean = true,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true
) {
    data object Connect : Screens(
        titleRes = R.string.connect_name,
        screen = "connect_screen",
        iconRes = R.drawable.baseline_qr_code_scanner
    )

    data object Devices : Screens(
        titleRes = R.string.devices_name,
        screen = "devices_screen",
        iconRes = R.drawable.baseline_bluetooth
    )

    data object Map : Screens(
        titleRes = R.string.map_name,
        screen = "map_screen",
        iconRes = R.drawable.ic_home_black_24dp
    )

    data object Settings : Screens(
        titleRes = R.string.settings_name,
        screen = "settings_screen",
        iconRes = R.drawable.ic_dashboard_black_24dp,
        showIconOnBottomBar = false,
        showTopBar = false,
        showBottomBar = false
    )

    data object ChangeBLEItem : Screens(
        titleRes = R.string.device_changing,
        screen = "change_ble_item",
        iconRes = R.drawable.ic_dashboard_black_24dp,
        showIconOnBottomBar = false,
        showTopBar = false,
        showBottomBar = false
    )
}