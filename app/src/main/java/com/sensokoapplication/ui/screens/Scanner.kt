package com.sensokoapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sensokoapplication.R

@Composable
fun ScannerScreen(){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
        //.background(Color.LightGray)
    ) {
        Text("Box Scanner", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.h5)
        Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = "QR Code Image",alignment =  Alignment.Center)
    }
}