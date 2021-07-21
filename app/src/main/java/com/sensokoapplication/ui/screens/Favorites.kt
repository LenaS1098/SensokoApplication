package com.sensokoapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sensokoapplication.Transportbox
import com.sensokoapplication.mvvm.BoxViewModel


@ExperimentalFoundationApi
@Composable
fun FavoritesScreen( navController: NavController, boxViewModel: BoxViewModel){

    val allBoxesList = boxViewModel.getAllBoxes()
    val listArrivedBoxes = allBoxesList.value.filter { it.hasArrived }
    val listBoxes = allBoxesList.value.filter { !it.hasArrived }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Card(backgroundColor = MaterialTheme.colors.primary, modifier = Modifier.height(80.dp)) {
            Text("Meine Transportboxen", modifier = Modifier
                .padding(top = 25.dp, bottom = 15.dp)
                .fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary)
        }


        Text(text = "Aktuelle Lieferungen", modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), style = MaterialTheme.typography.h6)
        LazyColumn {
            items(listBoxes){
                item ->
                FancyItem(item = item, navController,boxViewModel)
            }
        }


        Text(text = "Vergangene Lieferungen", modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), style = MaterialTheme.typography.h6)
        LazyColumn {
            items(listArrivedBoxes){
                    item ->
                FancyItem(item = item, navController,boxViewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FancyItem(item: Transportbox, navController: NavController, boxViewModel: BoxViewModel){
    val changeCurrentBox = remember{ mutableStateOf(false)}
    Card(modifier = Modifier
        .padding(top = 10.dp, start = 20.dp, end = 20.dp)
        .combinedClickable(onLongClick = {
                                         boxViewModel.changeCurBox(item)
                                         navController.navigate("overview")
                                         }, onClick = {})
        ,
        shape = RoundedCornerShape(8.dp), backgroundColor = Color.LightGray
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
                .wrapContentWidth(Alignment.Start)) {
                Text(text = item.label, style = MaterialTheme.typography.h6, modifier = Modifier.padding(top = 3.dp))
                Text(text = if(item.hasArrived){item.arrival}else{item.origin},modifier = Modifier.padding(bottom = 3.dp))
                Divider()
            }
          /*  Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = "FavoriteIcon", modifier = Modifier
                .padding(end = 10.dp)
                .weight(1f)
                .wrapContentWidth(
                    Alignment.End
                )
                .clickable {

                }, contentScale = ContentScale.FillHeight)*/
        }

    }
   /* if(changeCurrentBox.value){
        BoxOverviewScreen(box = item)
    }*/
   
}