package com.example.tfgapplibros.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CampoSlider
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.components.CampoTextoLargo
import com.example.tfgapplibros.model.AddLibroViewModel
import com.example.tfgapplibros.model.Autentificacion


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibro(
    navController: NavHostController, viewModel: AddLibroViewModel = viewModel()
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
        AddLibroContenido(it = it, viewModel = viewModel);
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibroContenido(
    viewModel: AddLibroViewModel,
    it: PaddingValues,
) {
    val userActivo = Autentificacion.usuarioActualUid
    val listaGeneros = listOf("Ficción", "Ciencia ficción", "Fantasía", "Misterio", "Romance")

    val titulo by viewModel.titulo.observeAsState("")
    val autor by viewModel.autor.observeAsState("")
    val descripcion by viewModel.descripcion.observeAsState("")
    val imageUriSelec by viewModel.imgUri.observeAsState()
    val estadoLibro by viewModel.estado.observeAsState(1)
    //Dropdown
    val generoSeleccionado by viewModel.genero.observeAsState("")
    var expanded by remember { mutableStateOf(false) }
    //Imagen
    val placeholderId = R.drawable.ic_launcher_background
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { viewModel.newImagenSelec(uri) } }
    )
    val camposRellenos =
        titulo.isNotEmpty() && autor.isNotEmpty() && generoSeleccionado.isNotEmpty() && imageUriSelec != null

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
                onTextChanged = { viewModel.tituloChange(it) },
                label = "Título del Libro",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = autor,
                onTextChanged = { viewModel.autorChange(it) },
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
                    onValueChange = { viewModel.generoChange(it) },
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
                                viewModel.generoChange(selec)
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
                onValueChange = { viewModel.estadoChange(it.toInt()) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (imageUriSelec != null) {
                AsyncImage(
                    model = imageUriSelec,
                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .aspectRatio(3f / 4f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = placeholderId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .aspectRatio(3f / 4f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CampoTextoLargo(
                text = descripcion,
                onTextChanged = { viewModel.descripcionChange(it) },
                label = "Mas informacion"
            )

            Button(
                enabled = camposRellenos,
                onClick = {
                    if (userActivo != null) {
                        viewModel.guardarLibro(userActivo)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                Text(text = "Agregar")
            }
        }
    }
}

