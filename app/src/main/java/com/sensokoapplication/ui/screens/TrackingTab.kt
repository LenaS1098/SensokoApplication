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
import com.sensokoapplication.R
import com.sensokoapplication.db.Transport
import com.sensokoapplication.db.Transportbox

@Composable
fun TrackingTab(transport : Transport, transportbox: Transportbox){

    Column(horizontalAlignment = Alignment.Start) {
        TrackingStats(transport)
        Spacer(modifier = Modifier.padding(top = 15.dp))
        TrackStepCard(painterResource(id = R.drawable.ic_ready), label = "Transportbereit", data = transport.carrier,transport.ready)
        TrackStepCard(painterResource(id = R.drawable.ic_transport), label = "Ins Transportfahrzeug eingeladen", data = transport.transporter,transport.shipped)
        TrackStepCardNoDivider(painterResource(id = R.drawable.ic_finish), label = "Ankunft am Zielort", data = transportbox.arrival, transport.arrival)
        Spacer(modifier = Modifier.padding(top = 15.dp))
    }
    
}

@Composable
fun TrackStepCard(icon: Painter, label : String, data : String, isActive : Boolean){
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=25.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            ){
            CircleIcon(icon = icon, isActive)
            Column {
                Text(text = label, Modifier.paddingFromBaseline(top = 20.dp),fontSize = 15.sp)
                if(isActive){
                    Text(data, fontSize = 13.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
                }

            }
        }
        Divider(color = Color.Gray, modifier = Modifier
            .padding(start = 15.dp)
            .height(30.dp)
            .width(2.dp))
    }
}
@Composable
fun TrackStepCardNoDivider(icon: Painter, label : String, data : String, isActive: Boolean){
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=25.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ){
            CircleIcon(icon = icon, isActive)
            Column {
                Text(text = label, Modifier.paddingFromBaseline(top = 20.dp),fontSize = 15.sp)
                if(isActive){
                    Text(data, fontSize = 13.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
                }

            }
        }
    }
}

@Composable
fun CircleIcon(icon : Painter, isActive: Boolean){
    val backgroundColor = if(isActive){
        MaterialTheme.colors.primary
    }else{
        Color.LightGray
    }
    val openDialog: MutableState<Boolean> = remember{mutableStateOf(false)}
    Column(modifier = Modifier
        .fillMaxHeight(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Card(shape = CircleShape, elevation = 12.dp, backgroundColor = backgroundColor, modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
            .clickable {
                openDialog.value = true
            }
        ) {
            Image(painter = icon, contentDescription = "Circle Icon", contentScale = ContentScale.Crop)
        }
        /*if(openDialog.value){
            AlertDialog(onDismissRequest = { openDialog.value = false}
                , title = {Text("Zuständige Person")}
                , text = { Text("Logistiker XYZ")}
                ,confirmButton = {
                    Text("X", color = Color.Black, modifier = Modifier.clickable { openDialog.value = false })

                })
        }*/
    }
}

@Composable
fun TrackingStats(transportInfo: Transport){
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
                BasicInfoBox(label = "Startpunkt", info = transportInfo.origin)
                BasicInfoBox(label = "Ankunftsziel", info = transportInfo.destination)
                BasicInfoBox(label = "Transport", info = transportInfo.carrier)
            }
        }
    }
}