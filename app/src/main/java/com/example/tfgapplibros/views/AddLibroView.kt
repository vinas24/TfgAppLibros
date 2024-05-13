package com.example.tfgapplibros.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CampoSlider
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.components.CampoTextoLargo
import com.example.tfgapplibros.model.Autentificacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AddLibro(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Libro") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                })
        }
    ) {
        AddLibroContenido(it = it);
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibroContenido(
    it: PaddingValues,
) {
    val userActivo = Autentificacion.usuarioActualUid

    val listaGeneros = listOf("Ficción", "Ciencia ficción", "Fantasía", "Misterio", "Romance")
    val listaEstados = listOf("Mucho uso","Uso Moderado","Poco Uso","Como nuevo","Nuevo")

    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf(listaGeneros.first()) }
    var estadoLibro by remember { mutableIntStateOf(1) }
    var estadoLibroTexto by remember { mutableStateOf((listaEstados[estadoLibro-1]))}

    //var ImagenLibroUri by
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(top = 100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {

            CampoTexto(
                text = titulo,
                onTextChanged = { titulo = it },
                label = "Título del Libro",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                text = autor,
                onTextChanged = { autor = it },
                label = "Autor del Libro",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = generoSeleccionado,
                    onValueChange = { generoSeleccionado = it },
                    label = { Text("Genero") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    listaGeneros.forEach { selec ->
                        DropdownMenuItem(
                            text = { Text(selec) },
                            onClick = {
                                generoSeleccionado = selec
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            CampoSlider(
                value = estadoLibro.toFloat(),
                onValueChange = {estadoLibro = it.toInt()},
                label = "Estado:  $estadoLibroTexto", num = 5)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTextoLargo(text = descripcion, onTextChanged = {descripcion = it}, label = "Mas informacion")

            Button(
                onClick = { /* Agregar lógica para agregar el libro */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                Text(text = "Agregar")
            }
        }
    }
}