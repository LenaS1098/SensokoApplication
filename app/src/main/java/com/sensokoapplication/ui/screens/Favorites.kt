package com.sensokoapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.sensokoapplication.ui.navigation.Screen

@Composable
fun FavoritesScreen(dummyList: List<Screen.Box>){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Favorites")
    }
}