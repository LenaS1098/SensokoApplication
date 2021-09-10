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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sensokoapplication.R
import com.sensokoapplication.db.BoxViewModel
import com.sensokoapplication.db.Parameter
import com.sensokoapplication.db.Transportbox


@Composable
fun History(boxViewModel: BoxViewModel,kammer :MutableState<String>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ){
        Text("Kammer ${kammer.value}", modifier = Modifier.padding(bottom=10.dp),style = MaterialTheme.typography.h5)

    }
}


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
    val listeParameter = boxViewModel.listeParameter.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Text("Kammer ${kammer.value}", modifier = Modifier.padding(bottom=10.dp),style = MaterialTheme.typography.h5)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            InfoClickable(
                label = "Live Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog,
                listeParameter = listeParameter
            )
            InfoClickable(
                label = "Luftfeuchtigkeit",
                info = "52%",
                openDialog = openDialog,
                listeParameter = listeParameter
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            InfoClickable(label = "Lichstrahlung", info = "1", openDialog = openDialog, listeParameter = listeParameter)
            InfoClickable(
                label = "Ziel Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog,
                listeParameter = listeParameter
            )
        }

    }
}

@Composable
fun InfoClickable(label: String,
                  info: String,
                  color: Color = Color.LightGray,
                  openDialog :MutableState<Boolean>,
                  listeParameter: List<Parameter>
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
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                listeParameter.forEach {
                    Text(text = it.temperatur.toString()+"  ",modifier = Modifier.padding(top = 5.dp,end = 3.dp),fontStyle = FontStyle.Italic, fontSize = 15.sp)
                }
            }
        }
    }

     /*if(openDialog.value){
          AlertDialog(onDismissRequest = { openDialog.value = false}
          , title = {Text("")}
          , text = { Image(painter = painterResource(id = R.drawable.historytempbox), contentDescription = "History Temp")}
          ,confirmButton = {
                  Text("X", color = Color.Black, modifier = Modifier.clickable { openDialog.value = false })

              })
      }*/
}