package com.begunok.begunok.screens.connect

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.begunok.begunok.MainActivity
import com.begunok.begunok.data.db.MainDb
import com.journeyapps.barcodescanner.ScanOptions


@Composable
fun ConnectUI(
    pav: PaddingValues,
    bleItemsDb: MainDb,
    context: MainActivity,
    scanLauncher: ActivityResultLauncher<ScanOptions>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = pav.calculateTopPadding(), bottom = pav.calculateBottomPadding())
    ) {
        FastConnectUI(bleItemsDb, context, scanLauncher)
        //ScanConnectUI(context)
    }
}