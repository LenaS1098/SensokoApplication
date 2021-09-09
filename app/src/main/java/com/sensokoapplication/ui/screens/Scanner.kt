package com.sensokoapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sensokoapplication.db.BoxViewModel
import com.sensokoapplication.db.Kammer
import com.sensokoapplication.db.Transport
import com.sensokoapplication.db.Transportbox

//Textviews und Checkboxen, um Daten der Transportboxen/Transporte/Kammern aufzunehmen
//Dient als Ersatz zum Scannen
@Composable
fun ScannerScreen(boxViewModel: BoxViewModel){
    val boxId = boxViewModel.boxCount.value+1
    val transportId = boxViewModel.transportCount.value+1

    var showBoxInfos by remember{mutableStateOf(false)}
    var showTransportInfos by remember{mutableStateOf(false)}
    var showKammerInfos by remember{mutableStateOf(false)}

    //Variablen Transportbox
    var label by remember { mutableStateOf("") }
    var hasArrived by remember { mutableStateOf(false) }
    var arrival by remember { mutableStateOf("") }

    //Variablen Transport
    var carrier by remember { mutableStateOf("") }
    var transporter by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var ready by remember { mutableStateOf(false) }
    var shipped by remember { mutableStateOf(false) }
    var arrivalTransport by remember { mutableStateOf(false) }
    var stops by remember { mutableStateOf(-1) }

    //Variablen KammerA
    var goalTempA by remember { mutableStateOf("") }
    var medizinA by remember { mutableStateOf("") }

    //Variablen KammerB
    var goalTempB by remember { mutableStateOf("") }
    var medizinB by remember { mutableStateOf("") }

    //Variablen KammerC
    var goalTempC by remember { mutableStateOf("") }
    var medizinC by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
        //.background(Color.LightGray)
    ) {
        Text("Box Scanner", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.h5)
        // Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = "QR Code Image",alignment =  Alignment.Center)

        Text("Box Infos", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth().clickable {
                     showBoxInfos = !showBoxInfos
            }, textAlign = TextAlign.Center, style = MaterialTheme.typography.h6)
        if(showBoxInfos){
            OutlinedTextField(value = label, onValueChange = {label = it}, label = { Text(text = "Label*")})
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text("Angekommen:    ")
                Checkbox(checked = hasArrived, onCheckedChange = {
                    hasArrived = it
                    if(!hasArrived){arrival =""}
                } )
            }
            if(hasArrived){
                OutlinedTextField(value = arrival, onValueChange = {arrival = it}, label = { Text(text = "Ankunftszeit*")})
            }
        }
        Text("Transport Infos", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth().clickable {
                showTransportInfos = !showTransportInfos
            }, textAlign = TextAlign.Center, style = MaterialTheme.typography.h6)
        if(showTransportInfos){
            OutlinedTextField(value = carrier, onValueChange = {carrier = it}, label = { Text(text = "Unternehmen*")})
            OutlinedTextField(value = transporter, onValueChange = {transporter = it}, label = { Text(text = "Transporter*")})
            OutlinedTextField(value = origin, onValueChange = {origin = it}, label = { Text(text = "Startort*")})
            OutlinedTextField(value = destination, onValueChange = {destination = it}, label = { Text(text = "Ziel*")})
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text("Ready:")
                Checkbox(checked = ready, onCheckedChange = {
                    ready = it
                    if(!ready){
                        shipped = false
                        arrivalTransport = false
                    }
                })
            }
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text("Shipped:")
                Checkbox(checked = shipped, onCheckedChange = {
                    shipped = it
                    if(!shipped){
                        arrivalTransport = false
                    }else{
                        ready = true
                    }
                })
            }
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text("Angekommen:")
                Checkbox(checked = arrivalTransport, onCheckedChange = {
                    arrivalTransport = it
                    if(arrivalTransport){
                        ready = true
                        shipped = true
                    }
                })
            }
        }

        Text("Kammer Infos", modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth().clickable {
                showKammerInfos = !showKammerInfos
            }, textAlign = TextAlign.Center, style = MaterialTheme.typography.h6)
        if(showKammerInfos){
            OutlinedTextField(value = goalTempA, onValueChange = {goalTempA = it}, label = { Text(text = "Ziel Temeperatur*")})
            OutlinedTextField(value = medizinA, onValueChange = {medizinA = it}, label = { Text(text = "Medizin*")})
            OutlinedTextField(value = goalTempB, onValueChange = {goalTempB = it}, label = { Text(text = "Ziel Temeperatur*")})
            OutlinedTextField(value = medizinB, onValueChange = {medizinB = it}, label = { Text(text = "Medizin*")})
            OutlinedTextField(value = goalTempC, onValueChange = {goalTempC = it}, label = { Text(text = "Ziel Temeperatur*")})
            OutlinedTextField(value = medizinC, onValueChange = {medizinC = it}, label = { Text(text = "Medizin*")})
        }

        Button(modifier = Modifier.padding(top = 10.dp),
            onClick = {
                val transport = Transport(carrier, transporter, origin, destination)
                boxViewModel.insertTransport(transport)
                val transportbox = Transportbox(label,transportId,hasArrived, arrival)
                boxViewModel.insertBox(transportbox)


                    val kammerA = Kammer(boxId,goalTempA.toFloat(),medizinA)
                    val kammerB = Kammer(boxId,goalTempB.toFloat(),medizinB)
                    val kammerC = Kammer(boxId,goalTempC.toFloat(),medizinC)
                    boxViewModel.insertKammer(kammerA)
                    boxViewModel.insertKammer(kammerB)
                    boxViewModel.insertKammer(kammerC)


                Log.e("Insert","Box inserted")
            }, content = {Text("Scan")})
    }
}



