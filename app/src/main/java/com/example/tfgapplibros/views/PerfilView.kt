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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tfgapplibros.AddLibroScreen
import com.example.tfgapplibros.LibroScreen
import com.example.tfgapplibros.PrincipalScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.CajaGenero
import com.example.tfgapplibros.components.CartaLibroPerfil
import com.example.tfgapplibros.components.acortarTxt
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.Usuario
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.PerfilViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(
    navController: NavHostController,
    userId: String
) {
    val esMio = Autentificacion.usuarioActualUid == userId
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                //TODO: cambiar el titulo dependiendo si es o no es el perfil de nuestro usuario
                title = {
                    if(esMio) {
                        Text("Mi Perfil")
                    } else {
                        Text(text = "Su Perfil")
                    }
                        },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = getColorFromResource(colorResId = R.color.primary_dark),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(PrincipalScreen)
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
        Contenido(it = it, navController = navController, userId = userId, esMio = esMio);
    }
}

@Composable
fun Contenido(
    it: PaddingValues,
    navController: NavHostController,
    userId: String,
    esMio: Boolean
) {
    val viewModel: PerfilViewModel = viewModel()
    val libros by viewModel.libros.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()
    val datosUser by viewModel.datosUser.collectAsState()


    LaunchedEffect(userId) {
        viewModel.obtenerLibros(userId = userId)
        viewModel.cargarPerfil(userId)
        viewModel.obtenerFavoritos(userId = userId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 85.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(thickness = 2.dp, color = Color.Gray)
        DatosPerfil(datosUser)
        Libros(navController,esMio, libros, favoritos)
    }
}

@Composable
fun DatosPerfil(
    datosUser: Usuario?,
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
                .padding(horizontal = 16.dp)
        ) {
            if(datosUser != null) {
                AsyncImage(
                    model = datosUser.fotoPerfil,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(120.dp)
                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                        .padding(2.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = getColorFromResource(colorResId = R.color.primary))
                        .align(alignment = Alignment.CenterVertically)
                ) {
                    CircularProgressIndicator(color = getColorFromResource(colorResId = R.color.secondary_dark))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Biografia(
                datosUser = datosUser,
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
                    if (datosUser != null) {
                        items(datosUser.generos) { gen ->
                            CajaGenero(nombre = gen, 14.sp)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Biografia(
    datosUser: Usuario?,
    modifier: Modifier = Modifier,

) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(text = datosUser?.nombreUsuario?:"...", fontSize = 20.sp, color = getColorFromResource(colorResId = R.color.background_dark), fontWeight = FontWeight.Bold)
        Text(text = "${datosUser?.ciudad?:"..."}, ${datosUser?.pais?:"..."}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = datosUser?.biografia?:".....".acortarTxt(100),
            fontSize = 16.sp,
            lineHeight = 18.sp,
            color = Color.White
        )

    }
}

@Composable
fun Libros(
    navController: NavHostController,
    esMio: Boolean,
    libros: List<Libro>,
    favoritos: List<Libro>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Divider(color = Color.Gray)
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier,
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(it[selectedTabIndex]),
                    color = getColorFromResource(colorResId = R.color.secondary_dark)
                )
            }

        ) {
            Tab(selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    if (esMio) {
                        Text(text = "Mis libros", color = Color.Black)
                    } else {
                        Text(text = "Sus libros", color = Color.Black)

                    }
                       },
                selectedContentColor = Color.Gray,
                modifier = Modifier.background(getColorFromResource(colorResId = R.color.background_dark))
            )
            if(esMio) {
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text(text = "Favoritos", color = Color.Black) },
                    selectedContentColor = Color.Gray,
                    modifier = Modifier.background(getColorFromResource(colorResId = R.color.background_dark))
                )
            }
        }
        when (selectedTabIndex) {
            0 -> MisLibros(libros, navController)
            1 -> Favoritos(favoritos, navController)
        }
    }
}

@Composable
fun MisLibros(libros: List<Libro>, navController: NavHostController) {
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

@Composable
fun Favoritos(favoritos:List<Libro>,navController: NavHostController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 178.dp),
        modifier = Modifier
            .background(color = getColorFromResource(colorResId = R.color.background_light))
            .fillMaxSize()
    ) {
        items(favoritos) { libro ->
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

