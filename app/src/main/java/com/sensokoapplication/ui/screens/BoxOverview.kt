package com.sensokoapplication.ui.screens

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sensokoapplication.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Observer
import com.sensokoapplication.db.*
import java.util.*


@Composable
fun BoxOverviewScreen(boxViewModel: BoxViewModel) {
 //   val listBoxes = boxViewModel.getAllBoxes().filter { !it.hasArrived }
    val transportbox = boxViewModel.currentBox.value

    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val currentKammer = remember { mutableStateOf("A") }
            BoxHeader(transportbox, currentKammer,boxViewModel)
            TabHeader(transportbox, currentKammer, boxViewModel)
        }
    }
}

@Composable
fun BoxHeader(transportbox: Transportbox, currentKammer : MutableState<String>,boxViewModel: BoxViewModel) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            // shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text(
                        text = transportbox.label,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                Image(
                    painter = painterResource(id = R.drawable.transportbox),
                    contentDescription = " Platzhalterbild Transportbox",
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp)
                ) {
                    Text("A", modifier = Modifier.clickable {
                        currentKammer.value = "A"
                        boxViewModel.currentKammer.value = boxViewModel.listeKammern.value[0]
                        boxViewModel.changeCurKammer(boxViewModel.listeKammern.value[0])
                    })
                    Text("B", modifier = Modifier.clickable {
                        currentKammer.value = "B"
                        boxViewModel.currentKammer.value = boxViewModel.listeKammern.value[1]
                        boxViewModel.changeCurKammer(boxViewModel.listeKammern.value[1])
                    })
                    Text("C", modifier = Modifier.clickable {
                        currentKammer.value = "C"
                        boxViewModel.currentKammer.value = boxViewModel.listeKammern.value[2]
                        boxViewModel.changeCurKammer(boxViewModel.listeKammern.value[2])

                    })

                }
            }


        }
    }

}

@Composable
fun BasicInfoBox(label: String, info: String) {
    Column() {
        Text(
            text = label,
            modifier = Modifier.padding(top = 5.dp),
            color = MaterialTheme.colors.onSecondary,
            fontSize = 15.sp,
           // fontStyle = FontStyle.Italic
        )
        Text(
            text = info,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp),
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}


@Composable
fun OverviewTab(transportbox: Transportbox, kammer: MutableState<String>, boxViewModel: BoxViewModel){
    val openDialog = remember { mutableStateOf(false) }
    val currentTransport = boxViewModel.currentTransport.value
    val kammerInt = when (kammer.value) {
        "A" -> 0
        "B" -> 1
        "C" -> 2
        else -> 1
    }
    boxViewModel.getKammernFromBox(transportbox)
    val listKammern = if(boxViewModel.listeKammern.value.isEmpty()){
        DummyKammern()
    }else{
        boxViewModel.listeKammern.value
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        TrackingStats(currentTransport)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailInfoBox(
                label = "Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog
            )
            DetailInfoBox(
                label = "Inhalt",
                info = listKammern[kammerInt].medizin,
                openDialog = openDialog
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            DetailInfoBox(label = "Aktuelle Kammer", info = kammer.value, openDialog = openDialog)
            DetailInfoBox(
                label = "Ziel Temperatur",
                info = listKammern[kammerInt].goalTemp.toString(),
                openDialog = openDialog
            )
        }
    }
}

@Composable
fun DetailInfoBox(
    label: String,
    info: String,
    color: Color = Color.LightGray,
    openDialog: MutableState<Boolean>
) {

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
                //  modifier = Modifier.clickable(onClick = { openDialog.value = true
                // Log.e("alertdialog","clicked") } )
            )
            Text(text = info, modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
fun TabHeader(transportbox: Transportbox, currentKammer: MutableState<String>, boxViewModel: BoxViewModel) {
    val state = remember { mutableStateOf(0) }
    val titles = listOf("Aktuell", "Sendeverlauf", "Historie")
    val currentTransport = boxViewModel.currentTransport.value
    Column {
        TabRow(
            selectedTabIndex = state.value,
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ) {
            titles.forEachIndexed { index, title ->
                FancyTab(
                    title = title,
                    onClick = { state.value = index },
                    selected = (index == state.value)
                )
            }
        }
        when (state.value) {
            0 -> {
                OverviewTab(transportbox, currentKammer, boxViewModel)
            }
            1-> {
                TrackingTab(currentTransport, transportbox)}
            2-> {
                HistoryTab(transportbox = transportbox,boxViewModel= boxViewModel, kammer = currentKammer)}
            else -> {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Fancy tab ${state.value + 1} selected",
                    style = MaterialTheme.typography.body1
                )
            }
        }

    }
}


fun DummyKammern(): List<Kammer> {
    val kammerA = Kammer(parentBoxId = 1, currentTemp = 2f, medizin = "Paracetamol")
    val kammerAB = Kammer(parentBoxId = 1, currentTemp = 2f, medizin = "Ibuprofen")
    val kammerAC = Kammer(parentBoxId = 1, currentTemp = 2f, medizin = "Aspirin")
    val kammerB = Kammer(2, 50f, "Nasenspray")
    val kammerC = Kammer(2, 50f, "Antibiotikum")
    val kammerD = Kammer(2, 50f, "Hustensaft")
    val ausgangsliste = listOf(kammerA, kammerAB, kammerAC, kammerB, kammerC, kammerD)
    return listOf(ausgangsliste.random(), ausgangsliste.random(), ausgangsliste.random())
}

@Composable
fun FancyTab(title: String, selected: Boolean, onClick: () -> Unit) {
    Tab(selected, onClick) {
        /*  Column(
              Modifier
                  .padding(5.dp)
                  .fillMaxWidth(),
              verticalArrangement = Arrangement.SpaceBetween
          ) {
              Box(
                  Modifier
                      .size(10.dp)
                      .align(Alignment.CenterHorizontally)
                      .background(color = if (selected) Color.Red else Color.White)
              )*/
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = if (selected) Color.Black else Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp, bottom = 5.dp)
        )
        //}
    }
}

