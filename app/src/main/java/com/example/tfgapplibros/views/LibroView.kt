package com.example.tfgapplibros.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfgapplibros.AddLibroScreen
import com.example.tfgapplibros.PerfilScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CajaGenero
import com.example.tfgapplibros.components.ImagenConLikeButton
import com.example.tfgapplibros.components.RatingStar
import com.example.tfgapplibros.components.acortarTxt
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.LibroViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun LibroView(
    navController: NavHostController,
    libroId: String,
    userId: String,
    viewModel: LibroViewModel = viewModel()
) {
    var libro by remember { mutableStateOf<Libro?>(null) }

    SideEffect {
        viewModel.checkLiked(libroId)
        viewModel.obtenerLibro(libroId, userId) { lib ->
            libro = lib
        }
    }

    if (libro != null) {
        ContenidoLibro(libro!!, userId, navController, viewModel = viewModel)
    } else {
        //TODO: tocara hacer un mensaje de error o algo
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoLibro(
    libro: Libro,
    userId: String,
    navController: NavHostController,
    viewModel: LibroViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles del libro") },
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
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (libro.userId == Autentificacion.usuarioActualUid) {
                        var expanded by remember { mutableStateOf(false) }

                        IconButton(
                            onClick = { expanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                tint = Color.White,
                                contentDescription = "Mas opciones"

                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            Modifier.background(getColorFromResource(colorResId = R.color.background_dark))
                        ) {
                            DropdownMenuItem(
                                text = { Text("Modificar", color = Color.Black) },
                                onClick = {
                                    expanded = false
                                    navController.navigate(AddLibroScreen(userId = libro.userId, libroId = libro.libroId))
                                })
                            Divider(modifier = Modifier.padding(horizontal = 8.dp))
                            DropdownMenuItem(
                                text = { Text("Eliminar", color = Color.Black) },
                                onClick = {
                                    expanded = false
                                    viewModel.borrarLibro(libro) {
                                        navController.navigate(PerfilScreen(userId = libro.userId))
                                    }
                                })
                        }
                    } else {
                        IconButton(onClick = {
                            navController.navigate(PerfilScreen(userId = libro.userId))
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                tint = Color.White,
                                contentDescription = null)
                        }
                    }

                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = getColorFromResource(colorResId = R.color.background_light)),
            verticalArrangement = Arrangement.Top,

            ) {
            val liked = viewModel.liked.observeAsState(false)
            val context = LocalContext.current
            val courutineScope = rememberCoroutineScope()

            fun mensaje(text: String){
                courutineScope.launch {
                    Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
                }
            }
            //TODO: Pasar una accion Para que el action button solo salga si no es nuestro el libro
            ImagenConLikeButton(
                libro = libro,
                userId = userId ,
                liked = liked.value
            ) {
                viewModel.likeChange(libro){
                    mensaje(text = it)
                }
            }

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                Divider(thickness = 2.dp, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        text = libro.titulo.acortarTxt(14),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    CajaGenero(nombre = libro.genero, 14.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = libro.autor.acortarTxt(20),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    RatingStar(estado = libro.estado)

                }
                Text(
                    text = libro.descripcion,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }

    }

}


