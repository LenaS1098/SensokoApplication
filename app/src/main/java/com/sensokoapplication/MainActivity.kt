package com.sensokoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.sensokoapplication.db.BoxDao
import com.sensokoapplication.db.BoxDatabase
import com.sensokoapplication.db.RoomBoxRepository
import com.sensokoapplication.mvvm.BoxViewModel
import com.sensokoapplication.mvvm.BoxViewState
import com.sensokoapplication.mvvm.HardCodedBoxRepository
import com.sensokoapplication.ui.screens.BoxOverviewScreen
import com.sensokoapplication.ui.theme.SensokoApplicationTheme
import com.sensokoapplication.ui.navigation.Screen
import com.sensokoapplication.ui.screens.FavoritesScreen
import com.sensokoapplication.ui.screens.LoginScreen
import com.sensokoapplication.ui.screens.ScannerScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {



        @ExperimentalFoundationApi
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            //Boxen/Kammern
            val box = Transportbox(1,"Box 1","GM","K",false,"",true)
            val boxB = Transportbox(2,"Box 2","GM","K",false)
            val boxD = Transportbox(3,"Box 3","GM","K",true,"12:45 Uhr - Köln")
            val kammerA = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Paracetamol")
            val kammerAB = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Ibu")
            val kammerAC = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Aspirin")
            val kammerB = Kammer(3,50f,"Ibu")
            val kammerC = Kammer(3,50f,"Ibu")
            val kammerD = Kammer(3,50f,"Aspirin")

            //create DB
            val boxDao : BoxDao = BoxDatabase.getInstance(this).boxDao

            lifecycleScope.launch {
                boxDao.insertBox(box)
                boxDao.insertBox(boxB)
                boxDao.insertBox(boxD)
                boxDao.insertKammer(kammerA)
                boxDao.insertKammer(kammerAB)
                boxDao.insertKammer(kammerAC)
                boxDao.insertKammer(kammerB)
                boxDao.insertKammer(kammerC)
                boxDao.insertKammer(kammerD)

            }

            val boxViewModel by viewModels<BoxViewModel>(factoryProducer = {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        val repository = RoomBoxRepository(boxDao)
                        @Suppress("UNCHECKED_CAST")
                        return BoxViewModel(boxRepository = repository) as T
                    }
                }})
                setContent {

                    SensokoApplicationTheme {
                        //LoginScreen(navController = rememberNavController())
                        BoxOverviewScaffoldNav(boxViewModel = boxViewModel)
                        /*val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "overview") {
                            composable("login") {
                                LoginScreen(navController)
                            }
                            composable("overview") {
                                BoxOverviewScaffoldNav(navController, boxViewModel)
                            }
                        }*/
                    }
                }
            }




        }




@ExperimentalFoundationApi
@Composable
fun BoxOverviewScaffoldNav(
    navController: NavHostController = rememberNavController(),
    boxViewModel: BoxViewModel
) {
    val items = listOf(
        Screen.Box,
        Screen.Scanner,
        Screen.Favorites
    )


    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.iconId),
                                contentDescription = screen.route
                            )
                        },
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
        NavHost(
            navController,
            startDestination = Screen.Box.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Box.route) { BoxOverviewScreen(boxViewModel) }
            composable(Screen.Scanner.route) { ScannerScreen() }
            composable(Screen.Favorites.route) { FavoritesScreen(navController, boxViewModel) }
        }
    }
}