package com.example.repuestosm.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

//opciondes del drawer
data class DrawerItem(
    val label: String,  //texto
    val icon: ImageVector,  //icono
    val onClick: () -> Unit  //uso del boton
)

@Composable  //ModalNavigationDrawer
fun AppDrawer(
    currentRoute: String?,
    items: List<DrawerItem>,  //lista de items
    modifier: Modifier = Modifier  //modificador opcional
) {
    ModalDrawerSheet(  //contenido del drawer
        modifier = modifier  //modificador encadenable
    ) {
        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.label) },
                selected = false,
                onClick = item.onClick,
                icon = { Icon(item.icon, contentDescription = item.label) },
                modifier = Modifier,
                colors = NavigationDrawerItemDefaults.colors()
            )
        }
    }
}

//helper para construir la lista estandar de items del drawer
@Composable
fun defaultDrawerItems(
    onHome: () -> Unit,  //accion en home
    onLogin: () -> Unit,  //accion en login
    onRegister: () -> Unit  //accion en registro
): List<DrawerItem> = listOf(
    DrawerItem("Home", Icons.Filled.Home, onHome),  //item home
    DrawerItem("Login", Icons.Filled.AccountCircle, onLogin),  //item login
    DrawerItem("Registro", Icons.Filled.Person, onRegister)  //item registro
)