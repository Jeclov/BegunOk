package com.begunok.begunok.screens.connect

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.begunok.begunok.MainActivity
import com.begunok.begunok.R
import com.begunok.begunok.add.item.fast.qrcode.ScanClass
import com.begunok.begunok.data.db.MainDb
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun FastConnectUI(bleItemsDb: MainDb, context: MainActivity, scanLauncher: ActivityResultLauncher<ScanOptions>) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.begunok_logo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {}, shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.baseline_nfc),
                    contentDescription = "NFC"
                )
                Text(modifier = Modifier.weight(1f), text = stringResource(R.string.nfc))
            }
        }
        Button(
            onClick = {
                ScanClass(context, bleItemsDb).scan(scanLauncher)

            }, shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner),
                    contentDescription = "QR code"
                )
                Text(modifier = Modifier.weight(1f), text = stringResource(R.string.scan_qr_code))
            }
        }
    }
}