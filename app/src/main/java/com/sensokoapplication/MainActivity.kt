package com.sensokoapplication

import android.os.Bundle
import android.util.Log
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

    private lateinit var boxDao: BoxDao
    private lateinit var transportDao: TransportDao
    private lateinit var kammerDao: KammerDao
    private lateinit var parameterDao: ParameterDao

    @ExperimentalFoundationApi
    override fun onStart() {
        super.onStart()

        boxDao = BoxDatabase.getInstance(this).boxDao
        transportDao = BoxDatabase.getInstance(this).transportDao
        kammerDao = BoxDatabase.getInstance(this).kammerDao
        parameterDao = BoxDatabase.getInstance(this).parameterDao


        val boxViewModel by viewModels<BoxViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    val repository = RoomBoxRepository(boxDao,transportDao, kammerDao, parameterDao)
                    @Suppress("UNCHECKED_CAST")
                    return BoxViewModel(boxRepository = repository) as T
                }
            }
        })

        setContent {
            SensokoApplicationTheme {
                LoginScreen(navController = rememberNavController(), boxViewModel)
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
