package com.example.tfgapplibros.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgapplibros.PerfilScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.BotonNormal
import com.example.tfgapplibros.components.CampoSlider
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.components.CampoTextoLargo
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.model.AddLibroViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibro(
    userId: String,
    navController: NavHostController,
    libroId: String? = null,
    viewModel: AddLibroViewModel = viewModel()
) {
    val esEditando = libroId != null

    Log.d("modifccccc",esEditando.toString())
    SideEffect {
        if (esEditando) {
            viewModel.cargarDetallesLibro(userId,libroId!!)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Libro") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = getColorFromResource(colorResId = R.color.primary_dark),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = ""
                        )
                    }
                })
        }
    ) {
        AddLibroContenido(
            viewModel = viewModel,
            navController = navController,
            userId = userId,
            esEditando = esEditando,
            libroId = libroId
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibroContenido(
    viewModel: AddLibroViewModel,
    navController: NavHostController,
    userId: String,
    esEditando: Boolean,
    libroId: String?
) {
    var listaGeneros = listOf("Auto-ayuda", "Fantasía", "Ficción", "Histórica","Infantil", "Juvenil", "Misterio", "Poesía", "Romance", "Terror" )


    val titulo by viewModel.titulo.observeAsState("")
    val autor by viewModel.autor.observeAsState("")
    val descripcion by viewModel.descripcion.observeAsState("")
    val imageUriSelec by viewModel.imgUri.observeAsState()
    val estadoLibro by viewModel.estado.observeAsState(1)
    //Dropdown
    val generoSeleccionado by viewModel.genero.observeAsState("")
    var expanded by remember { mutableStateOf(false) }
    //Imagen
    //TODO: Cambiar la imagen del placeholder, q ahora mismo es fea
    val placeholderId = R.drawable.libro
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { viewModel.newImagenSelec(uri) } }
    )
    val camposRellenos =
        titulo.isNotEmpty() && autor.isNotEmpty() && generoSeleccionado.isNotEmpty() && imageUriSelec != null


    if (viewModel.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .05f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = getColorFromResource(colorResId = R.color.primary))
        }
    }

    Surface(
        color = getColorFromResource(colorResId = R.color.background_light),
        modifier = Modifier.padding(top = 95.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            Spacer(modifier = Modifier.size(12.dp))
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
                val colorPrim = getColorFromResource(colorResId = R.color.primary)
                val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
                val colorBack = getColorFromResource(colorResId = R.color.background_light)

                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = generoSeleccionado,
                    onValueChange = { viewModel.generoChange(it) },
                    label = { Text("Género") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedLabelColor = colorPrim2,
                        focusedIndicatorColor = colorPrim2,
                        focusedContainerColor = colorBack,
                        cursorColor = colorPrim2,
                        unfocusedIndicatorColor = colorPrim,
                        unfocusedLabelColor = colorPrim,
                        unfocusedContainerColor = colorBack,
                        focusedTextColor = colorPrim2,
                        unfocusedTextColor = colorPrim,
                        focusedLeadingIconColor = colorPrim2,
                    ),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    Modifier.background(getColorFromResource(colorResId = R.color.background_light))
                ) {
                    listaGeneros.forEach { selec ->
                        DropdownMenuItem(
                            text = { Text(text = selec, color = Color.Black) },
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (esEditando) {
                    BotonNormal(texto = "Editar", enabled = camposRellenos) {
                        //TODO: Metodo actualizar libro
                        viewModel.actualizarlibro(userId, libroId!!) {
                            navController.navigate(
                                PerfilScreen(
                                    userId = userId
                                )
                            )
                        }
                    }
                } else {
                    BotonNormal(texto = "Agregar", enabled = camposRellenos) {
                        viewModel.guardarLibro(userId) {
                            navController.navigate(
                                PerfilScreen(
                                    userId = userId
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

