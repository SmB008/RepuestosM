package com.example.repuestosm.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.repuestosm.ui.theme.components.AppTopBar
import com.example.repuestosm.ui.theme.components.AppDrawer
import com.example.repuestosm.ui.theme.components.defaultDrawerItems
import com.example.repuestosm.ui.theme.screen.HomeScreen
import com.example.repuestosm.ui.theme.screen.LoginScreenVm
import com.example.repuestosm.ui.theme.screen.RegisterScreenVm

@Composable  //grafico de navegacion + drawer + scaffold
fun AppNavGraph(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()  //necesario para abrir y cerrar drawer

    //navegacion (topbar/drawer/botones)
    val goHome: () -> Unit    = { navController.navigate(Route.Home.path) }  //home
    val goLogin: () -> Unit   = { navController.navigate(Route.Login.path) }  //login
    val goRegister: () -> Unit = { navController.navigate(Route.Register.path) }  //registro

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = null,
                items = defaultDrawerItems(
                    onHome = {
                        scope.launch { drawerState.close() }
                        goHome()  //navega a home
                    },
                    onLogin = {
                        scope.launch { drawerState.close() }
                        goLogin()  //navega a login
                    },
                    onRegister = {
                        scope.launch { drawerState.close() }
                        goRegister() //navega a registro
                    }
                )
            )
        }
    ) {
        //pantalla
        Scaffold(
            topBar = { // Barra superior con íconos/menú
                AppTopBar(
                    onOpenDrawer = { scope.launch { drawerState.open() } }, // Abre drawer
                    onHome = goHome,  //boton home
                    onLogin = goLogin,  //boton login
                    onRegister = goRegister  //boton registro
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,  //controlador
                startDestination = Route.Home.path,  //inicio(home)
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.Home.path) {  //home
                    HomeScreen(
                        onGoLogin = goLogin,  //boton login
                        onGoRegister = goRegister  //boton registro
                    )
                }
                composable(Route.Login.path) {  //login
                    LoginScreenVm(
                        onLoginOkNavigateHome = goHome,  //si el VM success=true, navegamos a home
                        onGoRegister = goRegister  //enlace para ir a la pantalla de registro
                    )
                }
                composable(Route.Register.path) { // Destino Registro
                    RegisterScreenVm(
                        onRegisteredNavigateLogin = goLogin,  //si el VM success=true, volvemos a login
                        onGoLogin = goLogin  //boton alternativo para ir a login
                    )
                }
            }
        }
    }
}