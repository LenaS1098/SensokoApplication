package com.sensokoapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sensokoapplication.BoxOverviewScaffoldNav
import com.sensokoapplication.db.BoxViewModel


@Composable
@ExperimentalFoundationApi
fun LoginContent(state: MutableState<Boolean>){
    Column(modifier = Modifier
        .background(MaterialTheme.colors.primary)) {
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
                Text(text = "Login", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.padding(top = 30.dp))

                var email by remember { mutableStateOf("")}

                OutlinedTextField(value = email, onValueChange = {email = it}, label = { Text(text = "E-Mail")})

                var password by rememberSaveable { mutableStateOf("") }
                Spacer(Modifier.padding(top = 15.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Button(onClick = {
                    Log.e("loginDaten",email+password)

                    if(email=="testmail.de"&&password=="passwort"){
                        Log.e("login","success")
                        state.value = false
                      //  navController.navigate("overview")
                    }else{
                        Log.e("login","fail")
                    }
                },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("OK")
                }
            }
        }

    }
}


@ExperimentalFoundationApi
@Composable
fun LoginScreen(navController: NavController = rememberNavController(), boxViewModel: BoxViewModel) {
    //screen shown; true -> login, false -> BoxOverview
    val state = remember { mutableStateOf(true) }
    Scaffold() {
        
        BoxOverviewScaffoldNav(boxViewModel = boxViewModel)
     /*   if(state.value){
            LoginContent(state = state)
        }else{
            BoxOverviewScaffoldNav(boxViewModel = boxViewModel)
        }*/
    }

}

@Composable
fun ImportantTextField(label:String){
    var text by rememberSaveable {
        mutableStateOf("")
    }
}