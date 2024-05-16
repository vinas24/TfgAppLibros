package com.example.tfgapplibros.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgapplibros.components.RatingStar
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.LibroViewModel


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
        viewModel.obtenerLibro(libroId, userId) { lib ->
            libro = lib
        }
    }

    if (libro != null) {
        ContenidoLibro(libro!!, navController)
    } else {
        //TODO: tocara hacer un mensaje de error o algo
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoLibro(
    libro: Libro,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles del libro") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,

            ) {
            AsyncImage(
                model = libro.imgUrl,
                contentDescription = "Libro",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .padding(bottom = 16.dp),
            )

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                Divider(thickness = 2.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        text = libro.titulo,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    CajaGenero(nombre = libro.genero)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = libro.autor,
                        style = MaterialTheme.typography.titleLarge
                    )
                    RatingStar(estado = libro.estado)

                }
                Surface (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp)
                        .clip(
                            RoundedCornerShape(16.dp))
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    Text(text = libro.descripcion)
                }

            }

        }
    }
}
