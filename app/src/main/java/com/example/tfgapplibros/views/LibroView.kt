package com.example.tfgapplibros.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.LibroViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun LibroView(
    navController: NavHostController,
    libroId: String?,
    viewModel: LibroViewModel = viewModel()
) {
    val usuario = Autentificacion.usuarioActualUid
    var libro by remember { mutableStateOf<Libro?>(null) }

    SideEffect {
        Log.d("librooo2", "eeeeee")
        viewModel.obtenerLibro(libroId, usuario) { lib ->
            Log.d("librooo2", lib.toString())
            libro = lib
        }
    }

    Log.d("librooo", libro.toString())
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
                .padding(16.dp, top = 100.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                model = libro.imgUrl,
                contentDescription = "Libro",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(text = "FUncionoooo")
        }
    }
}
