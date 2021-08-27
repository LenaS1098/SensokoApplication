package com.sensokoapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sensokoapplication.R
import com.sensokoapplication.db.BoxViewModel
import kotlin.random.Random
import com.sensokoapplication.db.Transportbox

@Composable
fun ScannerScreen(boxViewModel: BoxViewModel){

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
        //.background(Color.LightGray)
    ) {
        Text("Box Scanner", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.h5)
        Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = "QR Code Image",alignment =  Alignment.Center)
        Button(onClick = {
            boxViewModel.insertBox(getRandomBox())
            Log.e("Insert","Box inserted")
        }, content = {Text("Scan")})
    }
}

fun getRandomBox(): Transportbox {
    val randomInt: Int = Random(20).nextInt()
    return Transportbox(label = "Inserted Box ",-1)
}