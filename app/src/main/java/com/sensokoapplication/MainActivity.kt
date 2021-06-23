package com.sensokoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sensokoapplication.ui.screens.BoxOverview
import com.sensokoapplication.ui.screens.LoginScreen
import com.sensokoapplication.ui.theme.SensokoApplicationTheme
import com.github.tehras.charts.*
import com.sensokoapplication.ui.navigation.Screen
import com.sensokoapplication.ui.screens.FavoritesScreen
import com.sensokoapplication.ui.screens.ScannerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensokoApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val dummyMedizinA = Medizin("Dummy Medizin A", 12f)
    val dummyMedizinB = Medizin("Dummy Medizin B", 22f)
    val dummyMedizinC = Medizin("Dummy Medizin C", 17f)
    val dummyMedizinD = Medizin("Dummy Medizin D", 31f)

    val dummyKammerA = Kammer("A", dummyMedizinA, 15f)
    val dummyKammerB = Kammer("A", dummyMedizinB, 25f)
    val dummyKammerC = Kammer("A", dummyMedizinC, 19f)
    val dummyKammerD = Kammer("A", dummyMedizinD, 30f)
    val dummyKammerListe : List<Kammer> = listOf(dummyKammerA,dummyKammerB,dummyKammerC,dummyKammerD)
    val dummyBoxA = Box("Box XYZ", dummyKammerListe,"Frankfurt", "Gummersbach")
    val dummyBoxB = Box("Box XYZ", dummyKammerListe,"Frankfurt", "Gummersbach")
    val dummyBoxC = Box("Box XYZ", dummyKammerListe,"Frankfurt", "Gummersbach")
    val dummyBoxD = Box("Box XYZ", dummyKammerListe,"Frankfurt", "Gummersbach")
    val dummyBoxE = Box("Box XYZ", dummyKammerListe,"Frankfurt", "Gummersbach")

    val dummyList: List<Box> = listOf(dummyBoxA,dummyBoxB,dummyBoxC,dummyBoxD,dummyBoxE)
    BottomNav(dummyBoxA,dummyList)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SensokoApplicationTheme {
        Greeting()
    }
}

@Composable
fun BottomNav( dummyBox : Box, dummyList: List<Box>){
    val items = listOf(
        Screen.Box,
        Screen.Scanner,
        Screen.Favorites
    )

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = screen.iconId),contentDescription = screen.route) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Box.route, Modifier.padding(innerPadding)) {
            composable(Screen.Box.route) { BoxOverview(box = dummyBox) }
            composable(Screen.Scanner.route) { ScannerScreen() }
            composable(Screen.Favorites.route){ FavoritesScreen(dummyList)}
        }
    }
}