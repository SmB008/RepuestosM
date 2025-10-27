package com.example.repuestosm.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
//pantalla home
fun HomeScreen(
    onGoLogin: () -> Unit,  //accion en login
    onGoRegister: () -> Unit  //accion en registro
) {
    val bg = MaterialTheme.colorScheme.surfaceVariant //fondo para pome

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(  //estructura vertical
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            //cabezera row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RepuestosM",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(8.dp))
                AssistChip(  //chip decorativo (Material 3)
                    onClick = {},  //boton sin accion
                    label = { Text(" ") }  //texto chip
                )
            }

            Spacer(Modifier.height(20.dp))

            //tarjeta con un mini “hero”
            ElevatedCard(  //card elevada para remarcar contenido
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Una tienda de calidad donde usted encarga y nosotros enviamos",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        " ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            //botones de navegacion principal
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = onGoLogin) { Text("Ir a Login") }  //navega a login
                OutlinedButton(onClick = onGoRegister) { Text("Ir a Registro") }  //a registro
            }
        }
    }
}