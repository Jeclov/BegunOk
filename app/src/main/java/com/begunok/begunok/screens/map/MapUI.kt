package com.begunok.begunok.screens.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.begunok.begunok.R
import com.begunok.begunok.ui.theme.MainCl

@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview
@Composable
fun MapUI(pav: PaddingValues) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = pav.calculateTopPadding(), bottom = pav.calculateBottomPadding())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.map), fontSize = 30.sp, color = MainCl)
        }
    }
}