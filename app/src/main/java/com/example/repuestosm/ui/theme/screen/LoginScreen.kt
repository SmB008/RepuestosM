package com.example.repuestosm.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.repuestosm.ui.theme.viewmodel.AuthViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.alpha


@Composable
//pantalla login conectada al VM
fun LoginScreenVm(
    onLoginOkNavigateHome: () -> Unit,  //navega a home cuando el login es exitoso
    onGoRegister: () -> Unit  //navega a registro
) {
    val vm: AuthViewModel = viewModel()  //crea/obtiene VM
    val state by vm.login.collectAsStateWithLifecycle()  //observa el StateFlow en tiempo real
    if (state.success) {
        vm.clearLoginResult()  //limpia banderas
        onLoginOkNavigateHome()
    }


    LoginScreen(
        email = state.email,
        pass = state.pass,
        emailError = state.emailError,
        passError = state.passError,
        canSubmit = state.canSubmit,
        isSubmitting = state.isSubmitting,  //loading
        errorMsg = state.errorMsg,
        onEmailChange = vm::onLoginEmailChange,
        onPassChange = vm::onLoginPassChange,
        onSubmit = vm::submitLogin,
        onGoRegister = onGoRegister
    )
}


@Composable
//pantalla login (solo navegacion, sin formularios)
private fun LoginScreen(


    email: String,
    pass: String,
    emailError: String?,
    passError: String?,
    canSubmit: Boolean,
    isSubmitting: Boolean,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onGoRegister: () -> Unit
) {
    val bg = MaterialTheme.colorScheme.secondaryContainer

    var showPass by remember { mutableStateOf(false) }  //estado local para mostrar/ocultar contraseña

    // -------- Animación de opacidad del botón según loading --------
    // Si isSubmitting=true → opacidad 0.6f, si no → 1f (transición suave)
    val buttonAlpha by animateFloatAsState(
        targetValue = if (isSubmitting) 0.6f else 1f,
        label = "alphaLoginButton"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(

            modifier = Modifier.fillMaxWidth()
                // -------- Ajuste suave del tamaño cuando aparecen/desaparecen errores --------
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Inicio de sesión",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Ingrese sus datos",
                textAlign = TextAlign.Left
            )
            Spacer(Modifier.height(20.dp))


            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                singleLine = true,
                isError = emailError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )
            // -------- MOSTRAR ERROR CON ANIMACIÓN SUAVE --------
            AnimatedVisibility(
                visible = emailError != null,
                enter = fadeIn() + expandVertically(),  //aparece con fade + expansion
                exit = fadeOut() + shrinkVertically()  //desaparece con fade + contraccion
            ) {
                if (emailError != null) {
                    Text(
                        emailError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(Modifier.height(8.dp))


            OutlinedTextField(
                value = pass,
                onValueChange = onPassChange,
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),  //toggle mostrar/ocultar
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPass) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                isError = passError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (passError != null) {
                Text(
                    passError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(Modifier.height(16.dp))

            // ---------- BOTÓN ENTRAR con opacidad animada ----------
            Button(
                onClick = onSubmit,
                enabled = canSubmit && !isSubmitting,
                modifier = Modifier.fillMaxWidth()
                    .alpha(buttonAlpha)  // Aplica opacidad animada al boton
            ) {
                if (isSubmitting) {  //UI de carga
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Validando...")
                } else {
                    Text("Entrar")
                }
            }

            if (errorMsg != null) {
                Spacer(Modifier.height(8.dp))
                Text(errorMsg, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            //boton de registro/crear cuenta
            OutlinedButton(onClick = onGoRegister, modifier = Modifier.fillMaxWidth()) {
                Text("Crear cuenta")
            }

        }
    }
}