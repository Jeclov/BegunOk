package com.begunok.begunok.add.item.fast.qrcode

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.begunok.begunok.MainActivity
import com.begunok.begunok.R
import com.begunok.begunok.add.item.fast.saveDevice
import com.begunok.begunok.data.db.MainDb
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ScanClass(
    private val context: MainActivity,
    private val bleItemsDb: MainDb
){
    fun scan(scanLauncher: ActivityResultLauncher<ScanOptions>) {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt(context.getString(R.string.scan_text))
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        scanLauncher.launch(options)
    }
    fun saveFastConnect(result: ScanIntentResult) {
        if (result.contents == null) {
            Log.d("TestLog", "Null camera object")
        } else {
            saveDevice(result.contents, bleItemsDb, context)
        }
    }
}





