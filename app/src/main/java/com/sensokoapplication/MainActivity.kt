package com.sensokoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.sensokoapplication.db.*
import com.sensokoapplication.db.BoxViewModel
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

            //Transport
            val transportA = Transport("Schmidt GmbH","Lieferwagen XYZ","GM","K")
            val transportB = Transport("Otto GmbH","Lieferwagen ABC","M","B")
            val transportC = Transport("Delta GmbH","Lieferwagen NOP","M","GM")

            val transportList = listOf<Transport>(transportA,transportB,transportC)


            //Boxen
            val boxA = Transportbox("Box A", transportA.transportId)
            val boxB = Transportbox("Box B", transportB.transportId, true, "12:51")
            val boxC = Transportbox("Box C", transportC.transportId)

            val boxList = listOf(boxA,boxB,boxC)

            //Kammern
            val kammerAA = Kammer(boxA.boxId,2f,"Paracetamol")
            val kammerAB = Kammer(boxA.boxId,4f,"Ibu")
            val kammerAC = Kammer(boxA.boxId,6f,"Aspirin")

            val kammerBA = Kammer(boxB.boxId,13f,"Medizin A")
            val kammerBB = Kammer(boxB.boxId,15f,"Medizin B")
            val kammerBC = Kammer(boxB.boxId,17f,"Medizin C")

            val kammerCA = Kammer(boxC.boxId,22f,"Medikament 1")
            val kammerCB = Kammer(boxC.boxId,24f,"Medikament 2")
            val kammerCC = Kammer(boxC.boxId,26f,"Medikament 3")

            val kammerList = listOf(kammerAA,kammerAB,kammerAC,kammerBA,kammerBB,kammerBC,kammerCA,kammerCB,kammerCC)

            //Parameter Box A
            val parameterAAA = Parameter(kammerAA.kammerId,"11:11",2.1f)
            val parameterAAB = Parameter(kammerAA.kammerId,"11:14",2.0f)
            val parameterAAC = Parameter(kammerAA.kammerId,"11:17",1.9f)

            val parameterABA = Parameter(kammerAB.kammerId,"11:11",4.1f)
            val parameterABB = Parameter(kammerAB.kammerId,"11:14",4.0f)
            val parameterABC = Parameter(kammerAB.kammerId,"11:17",3.9f)

            val parameterACA = Parameter(kammerAC.kammerId,"11:11",6.1f)
            val parameterACB = Parameter(kammerAC.kammerId,"11:14",6.0f)
            val parameterACC = Parameter(kammerAC.kammerId,"11:17",5.9f)

            //Parameter BoxB
            val parameterBAA = Parameter(kammerBA.kammerId,"11:11",13.1f)
            val parameterBAB = Parameter(kammerBA.kammerId,"11:14",13.0f)
            val parameterBAC = Parameter(kammerBA.kammerId,"11:17",12.9f)

            val parameterBBA = Parameter(kammerBB.kammerId,"11:11",15.1f)
            val parameterBBB = Parameter(kammerBB.kammerId,"11:14",15.0f)
            val parameterBBC = Parameter(kammerBB.kammerId,"11:17",14.9f)

            val parameterBCA = Parameter(kammerBC.kammerId,"11:11",17.1f)
            val parameterBCB = Parameter(kammerBC.kammerId,"11:14",17.0f)
            val parameterBCC = Parameter(kammerBC.kammerId,"11:17",16.9f)

            //Parameter BoxC
            val parameterCAA = Parameter(kammerCA.kammerId,"11:11",22.1f)
            val parameterCAB = Parameter(kammerCA.kammerId,"11:14",22.0f)
            val parameterCAC = Parameter(kammerCA.kammerId,"11:17",21.9f)

            val parameterCBA = Parameter(kammerCB.kammerId,"11:11",24.1f)
            val parameterCBB = Parameter(kammerCB.kammerId,"11:14",24.0f)
            val parameterCBC = Parameter(kammerCB.kammerId,"11:17",23.9f)

            val parameterCCA = Parameter(kammerCC.kammerId,"11:11",26.1f)
            val parameterCCB = Parameter(kammerCC.kammerId,"11:14",26.0f)
            val parameterCCC = Parameter(kammerCC.kammerId,"11:17",25.9f)

            val parameterList = listOf<Parameter>(
                parameterAAA,parameterAAB,parameterAAC,parameterABA,parameterABB,parameterABC,parameterACA,parameterACB,parameterACC,
                parameterBAA,parameterBAB,parameterBAC,parameterBBA,parameterBBB,parameterBBC,parameterBCA,parameterBCB,parameterBCC,
                parameterCAA,parameterCAB,parameterCAC,parameterCBA,parameterCBB,parameterCBC,parameterCCA,parameterCCB,parameterCCC
            )

            //create DB
            val boxDao : BoxDao = BoxDatabase.getInstance(this).boxDao

            lifecycleScope.launch {
                transportList.forEach {
                    boxDao.insertTransport(it)
                }
                boxList.forEach {
                    boxDao.insertBox(it)
                }
                kammerList.forEach {
                    boxDao.insertKammer(it)
                }
                parameterList.forEach {
                    boxDao.insertParameter(it)
                }
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
                        LoginScreen(navController = rememberNavController(),boxViewModel)
                      //  BoxOverviewScaffoldNav(boxViewModel = boxViewModel)
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
            composable(Screen.Scanner.route) { ScannerScreen(boxViewModel) }
            composable(Screen.Favorites.route) { FavoritesScreen(navController, boxViewModel) }
        }
    }
}