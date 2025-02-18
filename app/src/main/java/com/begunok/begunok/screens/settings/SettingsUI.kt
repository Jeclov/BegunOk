package com.begunok.begunok.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.begunok.begunok.R

@Composable
fun SettingsUI(navigationController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { navigationController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        // Logo
        Image(
            painter = painterResource(id = R.drawable.begunok_logo), // Замените на ваш ресурс логотипа
            contentDescription = "BegunOk logo",
            modifier = Modifier
                .size(128.dp)
                .border(2.dp, Color.Gray)
        )

        // Company name
        Text(
            text = stringResource(R.string.app_name),
            //style = typography.h5.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Buttons for theme
        //ThemeButton("Светлая", true)
        //ThemeButton("Темная", false)
        //ThemeButton("Как в системе", null)
    }
}