package com.example.tfgapplibros.views

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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.size.Size
import com.example.tfgapplibros.AddLibroScreen
import com.example.tfgapplibros.LibroScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CartaLibroPerfil
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.components.toColor
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.PerfilViewModel


val generos = listOf("Ficción", "Fantasía", "Misterio")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(
    navController: NavHostController,
    userId: String
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                //TODO: cambiar el titulo dependiendo si es o no es el perfil de nuestro usuario
                title = { Text("Mi Perfil") },
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
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddLibroScreen(userId = userId))
                },
                containerColor = getColorFromResource(colorResId = R.color.primary),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add libro",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                )
            }
        }
    ) {
        Contenido(it = it, navController = navController, userId = userId);
    }
}

@Composable
fun Contenido(
    it: PaddingValues,
    navController: NavHostController,
    userId: String,
) {
    val viewModel: PerfilViewModel = viewModel()
    val libros by viewModel.libros.collectAsState()

    LaunchedEffect(userId) {
        viewModel.obtenerLibros(userId = userId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 95.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(thickness = 2.dp, color = Color.Gray)
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
            .background(color = getColorFromResource(colorResId = R.color.primary))
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Mis Gustos:",
                        color = getColorFromResource(colorResId = R.color.background_dark),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp, top = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                {
                    items(generos) { gen ->
                        CajaGenero(nombre = gen, 14.sp)
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
        Text(text = "Nombre de Usuario", fontSize = 20.sp, color = getColorFromResource(colorResId = R.color.background_dark), fontWeight = FontWeight.Bold)
        Text(text = "Ciudad, Pais", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Una breve descripcion de uno mismo, con que libros le gusta leer y poco más.",
            fontSize = 16.sp,
            lineHeight = 18.sp,
            color = Color.White
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
        Divider(color = Color.Gray)
        Row(
            modifier = Modifier
                .background(color = getColorFromResource(colorResId = R.color.secondary))
                .fillMaxWidth()
        ) {
            Text(
                text = "Mis Libros:",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Divider(color = Color.Gray)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 178.dp),
            modifier = Modifier
                .background(color = getColorFromResource(colorResId = R.color.background_light))
                .fillMaxSize()
        ) {
            items(libros) { libro ->
                CartaLibroPerfil(libro = libro) {
                    navController.navigate(
                        LibroScreen(
                            userId = libro.userId,
                            libroId = libro.libroId
                        )
                    )
                }

            }

        }
    }
}

//TODO: Mover la imagen a Images.kt
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
    fontsize: TextUnit
) {
    Surface(
        modifier = Modifier
            .padding(2.dp),
        shape = RoundedCornerShape(25),
        color = nombre.toColor()
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nombre,
                color = Color.White,
                fontSize = fontsize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}