package com.example.tfgapplibros.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CartaLibroPerfil
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.PerfilViewModel


val generos = listOf("Ficción", "Fantasía", "Misterio")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        //TODO: Sustituir por icono flecha atras
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addlibro")
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add libro"
                )
            }
        }
    ) {
        Contenido(it = it, navController = navController);
    }
}

@Composable
fun Contenido(
    it: PaddingValues,
    navController: NavHostController,
) {
    val userId = Autentificacion.usuarioActualUid
    val viewModel: PerfilViewModel = viewModel()

    val libros by viewModel.libros.collectAsState()
    if (userId != null) {
        LaunchedEffect(userId) {
            viewModel.obtenerLibros(userId = userId)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //La topbar igual la cambiaré por una apptopbar
        DatosPerfil()
        MisLibros(navController, libros)
    }
}

@Composable
fun DatosPerfil(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            //Cambiar imagen a imagen de perfil
            ImagenRedonda(
                image = painterResource(
                    id = R.drawable.ic_launcher_foreground
                ),
                modifier = Modifier
                    .size(130.dp)
                    .weight(4f)
                    .padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Biografia(
                modifier = Modifier
                    .weight(7f)
                    .padding(end = 12.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            // Generos con el Lazy grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mis Gustos:",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp, top = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                {
                    items(generos) { gen ->
                        CajaGenero(nombre = gen)
                    }
                }
            }
        }
    }

}

@Composable
fun Biografia(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(text = "Nombre de Usuario", fontSize = 20.sp)
        Text(text = "Ciudad, Pais", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Una breve descripcion de uno mismo, con que libros le gusta leer y poco más.",
            fontSize = 16.sp,
            lineHeight = 18.sp
        )

    }
}

@Composable
fun MisLibros(
    navController: NavHostController,
    libros: List<Libro>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
        ) {
            Text(text = "Mis Libros:", fontSize = 20.sp, modifier = Modifier.padding(12.dp))
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 178.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            items(libros) {libro ->
                CartaLibroPerfil(libro = libro) {
                    //TODO: Esto hay que hacxerlo de tal modo que se vea el libro
                    navController.navigate("libro")
                }

            }

        }
    }
}

@Composable
fun ImagenRedonda(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(2.dp)
            .clip(CircleShape)
    )
}

@Composable
fun CajaGenero(
    nombre: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(50),
        color = Color.DarkGray
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nombre,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CajaLibro(
    nombre: String,
    image: Painter,
    modifier: Modifier = Modifier,
    navController: NavHostController

) {
    Surface(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(20),
        color = Color.LightGray,
        onClick = {
            navController.navigate("libro")
        }
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = modifier
                    .size(200.dp)
                    .aspectRatio(.85f, matchHeightConstraintsFirst = true)
                    .padding(2.dp)
            )
            Text(
                text = nombre,
                color = Color.Black,
                fontSize = 16.sp
            )

        }
    }
}