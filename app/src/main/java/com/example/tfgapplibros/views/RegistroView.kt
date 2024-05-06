package com.example.tfgapplibros.views

import android.net.Uri
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfgapplibros.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { "REGISTRO" },
                navigationIcon = {
                    IconButton(onClick = {
                        // Move back
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = ""
                        )
                    }
                })
        }
    ) {
        RegistrarUsuarioView(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarUsuarioView(
    it: PaddingValues
) {
    lateinit var imView: ImageView
    var idUsuario by remember { mutableStateOf(0) }
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf(0) }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf(0) }
    var numeroTelefono by remember { mutableStateOf(0) }
    var generos by remember { mutableStateOf(emptyList<String>()) }
    //var fotoPerfil by remember { mutableStateOf<Uri?>(null) }

    val pickImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) imView.setImageURI(uri)
        }

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuevo Lector",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )
        OutlinedTextField(
            value = nombre, onValueChange = { nombre = it },
            label = { Text(text = "Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = apellidos, onValueChange = { apellidos = it },
            label = { Text(text = "Apellidos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = usuario, onValueChange = { usuario = it },
            label = { Text(text = "Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = contraseña, onValueChange = { contraseña = it },
            label = { Text(text = "Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = edad.toString(),
            onValueChange = { newValue -> edad = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Edad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = correo, onValueChange = { correo = it },
            label = { Text(text = "Correo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = direccion, onValueChange = { direccion = it },
            label = { Text(text = "Dirección") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = pais, onValueChange = { pais = it },
            label = { Text(text = "País") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = ciudad, onValueChange = { ciudad = it },
            label = { Text(text = "Ciudad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = codigoPostal.toString(),
            onValueChange = { newValue -> codigoPostal = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Código Postal") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = numeroTelefono.toString(),
            onValueChange = { newValue -> numeroTelefono = newValue.toIntOrNull() ?: 0 },
            label = { Text(text = "Número Teléfono") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = generos.joinToString(),
            onValueChange = { generos = it.split(",").map { it.trim() } },
            label = { Text(text = "Géneros Literarios") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .blur(radiusX = 15.dp, radiusY = 15.dp)
        )
        Button(
            onClick = {
                //val b = Usuario(idUsuario, nombre, apellidos, usuario, contraseña, edad, correo, direccion, pais, ciudad, codigoPostal, numeroTelefono, generos)
            }
        ) {
            Text(text = "Registrar ")
        }
    }

}