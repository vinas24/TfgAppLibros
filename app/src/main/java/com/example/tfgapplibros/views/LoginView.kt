package com.example.tfgapplibros.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfgapplibros.components.CampoContrasena
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.model.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    Scaffold {
        Login(it, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    it: PaddingValues,
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    var usuario by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }
    val camposNoVacios = usuario.isNotEmpty() && passwd.isNotEmpty()

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )
        CampoTexto(
            text = usuario,
            onTextChanged = { usuario = it },
            label = "Nombre de Usuario",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        CampoContrasena(
            password = passwd,
            onPasswordChanged = { passwd = it },
            label = "Contrasena",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = "No tienes cuenta todavia?",
            modifier = Modifier.clickable(onClick = { navController.navigate("Registro") })
        )

        Spacer(
            modifier = Modifier.size(8.dp)
        )

        Button(
            enabled = camposNoVacios,
            onClick = {

                viewModel.signInConCorreoContrasena(email = usuario, passwd = passwd) {
                    navController.navigate("Principal")

                }
                //Reseteamos los campos
                usuario = ""
                passwd = ""

                //Para Probar la app, comentar el If y descomentar la siguente linea
                //navController.navigate("principal")
            }
        ) {
            Text(text = "Entrar")
        }
    }
}