package com.sensokoapplication.ui.screens

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sensokoapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun LoginScreen() {
    Column(modifier = Modifier
        .background(Color.Blue)) {
       // Image(painter = painterResource(id = R.drawable.header), contentDescription = "", contentScale = ContentScale.FillWidth )
        Column(
            modifier = Modifier
                // .background(Color.LightGray)
                .fillMaxWidth()
                .height(180.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "SensoKo", fontStyle = FontStyle.Italic, color = Color.White, fontSize = 22.sp)
            //Spacer(modifier = Modifier.padding(top = 12.dp))
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
            ,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Text(text = "Login as Transporter", fontFamily = FontFamily.Monospace)
                Spacer(modifier = Modifier.padding(top = 30.dp))

              //  var text by remember { mutableStateOf("")}

              // OutlinedTextField(value = text, onValueChange = {text = it}, label = { Text(text = "E-Mail")})


            }
        }

    }
}

@Composable
fun ImportantTextField(label:String){
    var text by rememberSaveable {
        mutableStateOf("")
    }
}