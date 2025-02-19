package com.begunok.begunok

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.begunok.begunok.add.item.fast.qrcode.ScanClass
import com.begunok.begunok.data.db.MainDb
import com.begunok.begunok.screens.menu.NavMenuUI
import com.begunok.begunok.ui.theme.BegunOkTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : ComponentActivity() {
    private lateinit var bleItemsDb: MainDb
    lateinit var navigationController: NavHostController
    private var scanLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ) { result ->
        ScanClass(this@MainActivity, bleItemsDb).saveFastConnect(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BegunOkTheme(darkTheme = false) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    bleItemsDb = MainDb.getInstance(this@MainActivity)
                    navigationController = rememberNavController()
                    NavMenuUI(navigationController, bleItemsDb, context = this, scanLauncher)
                }
            }
        }
    }
}

