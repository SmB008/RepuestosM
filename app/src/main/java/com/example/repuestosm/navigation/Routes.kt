package com.example.repuestosm.navigation

// Clase sellada para rutas
sealed class Route(val path: String) {
    data object Home     : Route("home")  //ruta de home
    data object Login    : Route("login")  //ruta de login
    data object Register : Route("register")  //ruta de registro
}
