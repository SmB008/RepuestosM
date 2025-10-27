package com.example.repuestosm.domain.validation

import android.util.Patterns


//validacion de mail
fun validateEmail(email: String): String? {
    if (email.isBlank()) return "El correo es obligatorio"   //no vacio
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()  //correo valido
    return if (!ok) "Formato de correo inválido" else null  //si no cumple, repetir
}


//validacion de nombre
fun validateNameLettersOnly(name: String): String? {
    if (name.isBlank()) return "El nombre es obligatorio"  //no vacio
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")  //solo letras, espacios y tildes
    return if (!regex.matches(name)) "Solo letras y espacios" else null //mensaje por error
}


//validacion telefono
fun validatePhoneDigitsOnly(phone: String): String? {
    if (phone.isBlank()) return "El teléfono es obligatorio"  //no vacío
    if (!phone.all { it.isDigit() }) return "Solo números"  //solo digitos
    if (phone.length !in 8..15) return "Debe tener entre 8 y 15 dígitos"  //numero real(tamaño)
    return null
}


//validacion de contraseña
fun validateStrongPassword(pass: String): String? {
    if (pass.isBlank()) return "La contraseña es obligatoria"  //no vacio
    if (pass.length < 8) return "Mínimo 8 caracteres"  //largo minimo
    if (!pass.any { it.isUpperCase() }) return "Debe incluir una mayúscula"  //al menos 1 mayuscula
    if (!pass.any { it.isLowerCase() }) return "Debe incluir una minúscula"  //al menos 1 minuscula
    if (!pass.any { it.isDigit() }) return "Debe incluir un número"  //al menos 1 numero
    if (!pass.any { !it.isLetterOrDigit() }) return "Debe incluir un símbolo"  //al menos 1 simbolo
    if (pass.contains(' ')) return "No debe contener espacios"  //sin espacios
    return null
}


//confirmacion de contraseña
fun validateConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"  //no vacio
    return if (pass != confirm) "Las contraseñas no coinciden" else null  //deben ser iguales
}