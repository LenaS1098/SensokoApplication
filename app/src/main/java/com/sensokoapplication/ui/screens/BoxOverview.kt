package com.sensokoapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sensokoapplication.Box
import com.sensokoapplication.R





@Composable
fun BoxOverview(box: Box){
    BoxHeader(box)
}

@Composable
fun BoxHeader(box: Box){
    val babyblue = Color(0xFF5cbdea )
    val tabState = remember { mutableStateOf(true) }
    val text = remember { mutableStateOf("Overview")}
    val currentKammer = remember{ mutableStateOf("A")}
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp), backgroundColor = babyblue) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = box.label, style = MaterialTheme.typography.h6, modifier = Modifier.padding(top = 10.dp))
                Image(painter = painterResource(id = R.drawable.transportbox), contentDescription = " Platzhalterbild Transportbox",
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 15.dp)){
                    Text("A", modifier =  Modifier.clickable {
                        currentKammer.value = "A"
                    })
                    Text("B", modifier =  Modifier.clickable {
                        currentKammer.value = "B"
                    })
                    Text("C", modifier =  Modifier.clickable {
                        currentKammer.value = "C"
                    })

                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    BasicInfoBox(label = "Startpunkt", info = box.origin)
                    BasicInfoBox(label = "Ankunftsziel", info = box.destination)
                    BasicInfoBox(label = "Ankuftszeit", info = "15:12 Uhr")
                }
            }
            

        }
        Row(modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()) {
            Text(text = text.value, modifier = Modifier
                .weight(1f)
                .padding(start = 30.dp)
                .wrapContentWidth(Alignment.Start), fontWeight = FontWeight.Thin, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.h5)
            Card(shape = RoundedCornerShape(3.dp), backgroundColor = Color.LightGray,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .wrapContentWidth(Alignment.End)
                    .weight(1f)
                    .height(25.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = {tabState.value = true }, enabled = true) {
                        Icon(painter = painterResource(id = R.drawable.ic_dashboard), contentDescription = "Dashboard Icon")
                    }
                    Divider(color = Color.DarkGray,modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp))
                    IconButton(onClick = { tabState.value = false}, enabled = true) {
                        Icon(painter = painterResource(id = R.drawable.ic_list), contentDescription = "List Icon")
                    }
                }
            }
        }
        text.value = when(tabState.value){
            true -> {
                OverviewTab(box = box, currentKammer.value.toString())
                "Overview"
            }
            false -> {

                ListTab(box, currentKammer.value)
                "Liste"
            }
        }

    }

}

@Composable
fun BasicInfoBox(label: String, info : String){
    Column() {
        Text(text = label, modifier = Modifier.padding(top = 5.dp), color = Color.White, fontSize = 15.sp)
        Text(text = info, modifier = Modifier.padding(top = 2.dp), color = Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun OverviewTab(box:Box, kammer : String = "A"){
    val openDialog = remember { mutableStateOf(false)}
    val kammerInt = when(kammer){
        "A" -> 0
        "B" -> 1
        "C" -> 2
        "D" -> 3
        else -> 1
    }

  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
      .fillMaxWidth()
      .padding(start = 15.dp, end = 15.dp, top = 15.dp)) {
      Row(horizontalArrangement = Arrangement.SpaceEvenly){
          DetailInfoBox(label = "Temperatur", info = box.listKammer[kammerInt].currentTemp.toString(),openDialog = openDialog)
          DetailInfoBox(label = "Inhalt", info = box.listKammer[kammerInt].inhalt.name, openDialog = openDialog)
      }
      Row(horizontalArrangement = Arrangement.SpaceEvenly){
          DetailInfoBox(label = "Aktuelle Kammer", info = kammer, openDialog = openDialog)
          DetailInfoBox(label = "Beste Temperatur", info = box.listKammer[kammerInt].inhalt.bestTemp.toString(), openDialog = openDialog)
      }

  }
}

@Composable
fun ListTab(box: Box, kammer : String = "A"){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .padding(start = 15.dp, end = 15.dp, top = 15.dp)) {
        Text(text = "Aktuelle Kammer:  $kammer")
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            items(box.getCurrentFacts(kammer)){
                    information ->
                Row(modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()){
                    Text(text = information.first,modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                        .wrapContentWidth(Alignment.Start))
                    Text(text = information.second,modifier = Modifier
                        .weight(1f)
                        .padding(end = 15.dp)
                        .wrapContentWidth(Alignment.End))
                }
            }
        }
    }
    
}

@Composable
fun DetailInfoBox(label: String, info: String, color: Color = Color.LightGray, openDialog : MutableState<Boolean>){

    Card(shape = RoundedCornerShape(8.dp), backgroundColor = color, modifier = Modifier
        .size(height = 140.dp, width = 200.dp)
        .padding(10.dp)
        ){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(text = label, modifier = Modifier.clickable(onClick = {
                openDialog.value = true
                Log.e("alertdialog","clicked")
            } ))
            Text(text = info, modifier = Modifier.padding(top = 5.dp))
        }
    }
    if(openDialog.value){
        AlertDialog(onDismissRequest = { openDialog.value = false}
        , title = {Text("")}
        , text = { Image(painter = painterResource(id = R.drawable.historytempbox), contentDescription = "History Temp")}
        ,confirmButton = {
                Text("X", color = Color.Black, modifier = Modifier.clickable { openDialog.value = false })
                
            })
    }
}



