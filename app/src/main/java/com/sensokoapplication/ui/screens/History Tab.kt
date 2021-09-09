package com.sensokoapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sensokoapplication.R
import com.sensokoapplication.db.BoxViewModel
import com.sensokoapplication.db.Transportbox

@Composable
fun HistoryTab(transportbox: Transportbox, kammer :MutableState<String>, boxViewModel: BoxViewModel){
    val openDialog = remember { mutableStateOf(false) }
    val kammerInt = when (kammer.value) {
        "A" -> 0
        "B" -> 1
        "C" -> 2
        else -> 1
    }
    val listKammern = boxViewModel.listeKammern.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth().padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Text("Kammer ${kammer.value}", modifier = Modifier.padding(bottom=10.dp),style = MaterialTheme.typography.h5)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            InfoClickable(
                label = "Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog
            )
            InfoClickable(
                label = "Luftfeuchtigkeit",
                info = "52%",
                openDialog = openDialog
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            InfoClickable(label = "Lichstrahlung", info = "1", openDialog = openDialog)
            InfoClickable(
                label = "Beste Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog
            )
        }

    }
}

@Composable
fun InfoClickable(label: String,
                  info: String,
                  color: Color = Color.LightGray,
                  openDialog :MutableState<Boolean>
){
    Card(
        shape = RoundedCornerShape(8.dp), backgroundColor = color, modifier = Modifier
            .size(165.dp)
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                  modifier = Modifier.clickable(onClick = { openDialog.value = true
                 } )
            )
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