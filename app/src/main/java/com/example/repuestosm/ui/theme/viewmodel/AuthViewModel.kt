package com.example.repuestosm.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.repuestosm.domain.validation.*


//estado de ui


data class LoginUiState(  //estado de la pantalla Login
    val email: String = "",
    val pass: String = "",
    val emailError: String? = null,
    val passError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)


data class RegisterUiState(  //estado de la pantalla registro
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val pass: String = "",
    val confirm: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passError: String? = null,
    val confirmError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)


//COLECCIÓN EN MEMORIA (solo para la demo)

//modelo minimo de usuario para la coleccion
private data class DemoUser(                               //datos a guardar en la coleccion
    val name: String,
    val email: String,
    val phone: String,
    val pass: String
)


class AuthViewModel : ViewModel() {

    //colección **estática** en memoria compartida entre instancias del VM (sin storage persistente)
    companion object {
        //lista mutable de usuarios para la demo (se pierde al cerrar la app)
        private val USERS = mutableListOf(
            //usuario por defecto para probar login:
            DemoUser(name = "Demo", email = "demo@duoc.cl", phone = "12345678", pass = "Demo123!")
        )
    }


    // Flujos de estado para observar desde la UI
    private val _login = MutableStateFlow(LoginUiState())
    val login: StateFlow<LoginUiState> = _login

    private val _register = MutableStateFlow(RegisterUiState())
    val register: StateFlow<RegisterUiState> = _register

    //login

    fun onLoginEmailChange(value: String) {
        _login.update { it.copy(email = value, emailError = validateEmail(value)) }  //guardar y validar
        recomputeLoginCanSubmit()
    }


    fun onLoginPassChange(value: String) {
        _login.update { it.copy(pass = value) }
        recomputeLoginCanSubmit()
    }


    private fun recomputeLoginCanSubmit() {
        val s = _login.value
        val can = s.emailError == null &&
                s.email.isNotBlank() &&
                s.pass.isNotBlank()
        _login.update { it.copy(canSubmit = can) }
    }


    fun submitLogin() {
        val s = _login.value
        if (!s.canSubmit || s.isSubmitting) return
        viewModelScope.launch {
            _login.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            delay(500) //simulación del tiempo de verificación


            val user = USERS.firstOrNull { it.email.equals(s.email, ignoreCase = true) }


            val ok = user != null && user.pass == s.pass


            _login.update {
                it.copy(
                    isSubmitting = false,
                    success = ok,
                    errorMsg = if (!ok) "Credenciales inválidas" else null
                )
            }
        }
    }


    fun clearLoginResult() {
        _login.update { it.copy(success = false, errorMsg = null) }
    }


    //registro: handlers y envío
    //nombre
    fun onNameChange(value: String) {
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _register.update {
            it.copy(name = filtered, nameError = validateNameLettersOnly(filtered))
        }
        recomputeRegisterCanSubmit()
    }

    //correo
    fun onRegisterEmailChange(value: String) {
        _register.update { it.copy(email = value, emailError = validateEmail(value)) }
        recomputeRegisterCanSubmit()
    }

    //telefono
    fun onPhoneChange(value: String) {
        val digitsOnly = value.filter { it.isDigit() }
        _register.update {
            it.copy(phone = digitsOnly, phoneError = validatePhoneDigitsOnly(digitsOnly))
        }
        recomputeRegisterCanSubmit()
    }

    //contraseña
    fun onRegisterPassChange(value: String) {
        _register.update { it.copy(pass = value, passError = validateStrongPassword(value)) }
        _register.update { it.copy(confirmError = validateConfirm(it.pass, it.confirm)) }
        recomputeRegisterCanSubmit()
    }

    //confirmacion contraseña
    fun onConfirmChange(value: String) {
        _register.update { it.copy(confirm = value, confirmError = validateConfirm(it.pass, value)) }
        recomputeRegisterCanSubmit()
    }

    //registro solo si todo esta correcto
    private fun recomputeRegisterCanSubmit() {
        val s = _register.value
        val noErrors = listOf(
            s.nameError,
            s.emailError,
            s.phoneError,
            s.passError,
            s.confirmError
        ).all { it == null }
        val filled =
            s.name.isNotBlank() && s.email.isNotBlank() && s.phone.isNotBlank() && s.pass.isNotBlank() && s.confirm.isNotBlank()
        _register.update { it.copy(canSubmit = noErrors && filled) }
    }


    fun submitRegister() {
        val s = _register.value
        if (!s.canSubmit || s.isSubmitting) return
        viewModelScope.launch {
            _register.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            delay(700)

            //¿Existe ya un usuario con el mismo email en la **colección**?
            val duplicated = USERS.any { it.email.equals(s.email, ignoreCase = true) }

            if (duplicated) { //si ya existe
                _register.update {
                    it.copy(isSubmitting = false, success = false, errorMsg = "El usuario ya existe")
                }
                return@launch
            }

            // Insertamos el nuevo usuario en la **colección** (solo demo; no persistimos)
            USERS.add(
                DemoUser(
                    name = s.name.trim(),
                    email = s.email.trim(),
                    phone = s.phone.trim(),
                    pass = s.pass
                )
            )

            _register.update {
                it.copy(isSubmitting = false, success = true, errorMsg = null)
            }
        }
    }


    fun clearRegisterResult() {
        _register.update { it.copy(success = false, errorMsg = null) }
    }
}