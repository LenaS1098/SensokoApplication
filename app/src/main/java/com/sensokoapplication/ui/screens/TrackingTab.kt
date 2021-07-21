package com.sensokoapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sensokoapplication.Transportbox
import com.sensokoapplication.R

@Composable
fun TrackingTab(transportbox: Transportbox){
    Column(horizontalAlignment = Alignment.Start) {
        TrackingStats(transportbox = transportbox)
        Spacer(modifier = Modifier.padding(top = 15.dp))
        TrackStepCard(painterResource(id = R.drawable.ic_star), label = "Fertig für Transport", data = "22.06.21 - 20:00 Uhr")
        TrackStepCard(painterResource(id = R.drawable.ic_star), label = "Ins Transportfahrzeug eingeladen", data = "23.06.21 - 04:30 Uhr")
        TrackStepCard(painterResource(id = R.drawable.ic_star), label = "Noch 5 Stopps entfernt", data = "23.06.21 - 12:15 Uhr")
        TrackStepCardNoDivider(painterResource(id = R.drawable.ic_star), label = "Ankunft am Zielort", data = "23.06.21 - 13:45 Uhr")
    }
    
}

@Composable
fun TrackStepCard(icon: Painter, label : String, data : String){
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=25.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            ){
            CircleIcon(icon = icon)
            Column {
                Text(text = label, Modifier.paddingFromBaseline(top = 20.dp),fontSize = 15.sp)
                Text(data, fontSize = 13.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
            }
        }
        Divider(color = Color.Gray, modifier = Modifier
            .padding(start = 15.dp)
            .height(30.dp)
            .width(2.dp))
    }
}
@Composable
fun TrackStepCardNoDivider(icon: Painter, label : String, data : String){
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=25.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ){
            CircleIcon(icon = icon)
            Column {
                Text(text = label, Modifier.paddingFromBaseline(top = 20.dp),fontSize = 15.sp)
                Text(data, fontSize = 13.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
            }
        }
    }
}



@Composable
fun CircleIcon(icon : Painter){
    val openDialog: MutableState<Boolean> = remember{mutableStateOf(false)}
    Column(modifier = Modifier
        .fillMaxHeight(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Card(shape = CircleShape, elevation = 12.dp, backgroundColor = MaterialTheme.colors.primary, modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .clickable {
                openDialog.value = true
            }
        ) {
            Image(painter = icon, contentDescription = "Circle Icon", contentScale = ContentScale.Crop)
        }
        if(openDialog.value){
            AlertDialog(onDismissRequest = { openDialog.value = false}
                , title = {Text("Zuständige Person")}
                , text = { Text("Logistiker XYZ")}
                ,confirmButton = {
                    Text("X", color = Color.Black, modifier = Modifier.clickable { openDialog.value = false })

                })
        }
    }
}

@Composable
fun TrackingStats(transportbox: Transportbox){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                BasicInfoBox(label = "Startpunkt", info = transportbox.origin)
                BasicInfoBox(label = "Ankunftsziel", info = transportbox.destination)
                BasicInfoBox(label = "Ankuftszeit", info = "15:12 Uhr")
            }
        }
    }
}