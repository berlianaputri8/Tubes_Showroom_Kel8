package id.ac.unpas.showroom.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.ac.unpas.showroom.ui.theme.Black
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val title = remember { mutableStateOf("") }
    val appBarHorizontalPadding = 4.dp
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Black,
                elevation = 0.dp,
                modifier= Modifier.fillMaxWidth())
            {
                //TopAppBar Content
                Box(Modifier.height(32.dp)) {
                    Row(
                        Modifier.fillMaxHeight()
                            .width(72.dp - appBarHorizontalPadding),
                        verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides
                                    ContentAlpha.high,
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                },
                                enabled = true,
                            ) {
                                Icon(
                                    Icons.Filled.Menu, null,
                                    tint = Color.White)
                            }
                        }
                    }
                    //  Title
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment =
                        Alignment.CenterVertically) {
                        ProvideTextStyle(value =
                        MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides
                                        ContentAlpha.high,
                            ){
                                Text(
                                    modifier =
                                    Modifier.fillMaxWidth(),
                                    textAlign =
                                    TextAlign.Center,
                                    color = Color.White,
                                    maxLines = 1,
                                    text = title.value
                                )
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
// reuse default SnackbarHost to have default  animation and timing handling
            SnackbarHost(it) { data ->
// custom snackbar with the custom colors
                Snackbar(
                    actionColor = Color.Green,
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        },
        drawerContent = {
            DrawerContent { route ->
                navController.navigate(route)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        bottomBar = {
            BottomNavigationComposable(title.value, onClick = { tab ->
                navController.navigate(tab.route)
            })
        },
    )
    { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    title.value = "Home"
                    HomeScreen()
                }
//route mobil
                composable("mobil") {
                    title.value = "Mobil"
                    MobilScreen(navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState, modifier =
                        Modifier.padding(innerPadding))
                }
                composable("tambah-mobil") {
                    title.value = "Tambah Mobil"
                    FormMobilScreen(navController =
                    navController, modifier = Modifier.padding(innerPadding))
                }
                composable("edit-mobil/{id}",
                    listOf(
                        navArgument("id") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                    title.value = "Edit Mobil"
                    val id =
                        backStackEntry.arguments?.getString("id")
                            ?: return@composable
                    FormMobilScreen(navController =
                    navController, id = id, modifier = Modifier.padding(innerPadding))
                }
//route motor
                composable("motor") {
                    title.value = "Motor"
                    MotorScreen(navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState, modifier =
                        Modifier.padding(innerPadding))
                }
                composable("tambah-motor") {
                    title.value = "Tambah Motor"
                    FormMotorScreen(navController =
                    navController, modifier = Modifier.padding(innerPadding))
                }
                composable("edit-motor/{id}",
                    listOf(
                        navArgument("id") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                    title.value = "Edit Motor"
                    val id =
                        backStackEntry.arguments?.getString("id")
                            ?: return@composable
                    FormMotorScreen(navController = navController, id = id, modifier =
                    Modifier.padding(innerPadding))
                }
//                route promo
                composable("promo") {
                    title.value = "Promo"
                    PromoScreen(navController = navController,
                        snackbarHostState = scaffoldState.snackbarHostState, modifier =
                        Modifier.padding(innerPadding))
                }
                composable("tambah-promo") {
                    title.value = "Tambah Promo"
                    FormPromoScreen(navController =
                    navController, modifier = Modifier.padding(innerPadding))
                }
                composable("edit-promo/{id}",
                    listOf(
                        navArgument("id") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                    title.value = "Edit Promo"
                    val id =
                        backStackEntry.arguments?.getString("id")
                            ?: return@composable
                    FormPromoScreen(navController = navController, id = id, modifier =
                    Modifier.padding(innerPadding))
                }
            }
        }
    }
}