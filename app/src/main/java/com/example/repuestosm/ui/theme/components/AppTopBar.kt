package com.example.repuestosm.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onOpenDrawer: () -> Unit, //abre drawer
    onHome: () -> Unit,       //accion en home
    onLogin: () -> Unit,      //accion en login
    onRegister: () -> Unit    //accion en registro
) {
    var showMenu by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {  //titulo
            Text(
                text = "Demo Navegación Compose",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        },
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {  //abre drawer
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            IconButton(onClick = onHome) {  //ir a home
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = onLogin) {  //ir a login
                Icon(Icons.Filled.AccountCircle, contentDescription = "Login")
            }
            IconButton(onClick = onRegister) {  //ir a registro
                Icon(Icons.Filled.Person, contentDescription = "Registro")
            }
            IconButton(onClick = { showMenu = true }) {  //abre el menu overflow
                Icon(Icons.Filled.MoreVert, contentDescription = "Más")
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }  //al tocar fuera se coerra
            ) {
                DropdownMenuItem(  //opcion home
                    text = { Text("Home") },
                    onClick = { showMenu = false; onHome() }  //navega y cierra
                )
                DropdownMenuItem(  //opcion login
                    text = { Text("Login") },
                    onClick = { showMenu = false; onLogin() }
                )
                DropdownMenuItem(  //opcion registro
                    text = { Text("Registro") },
                    onClick = { showMenu = false; onRegister() }
                )
            }
        }
    )
}