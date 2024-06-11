package com.example.tfgapplibros.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfgapplibros.LoginScreen
import com.example.tfgapplibros.PerfilScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.BusquedaViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.platform.LocalContext
import com.example.tfgapplibros.LibroScreen
import com.example.tfgapplibros.components.CartaLibroPerfil
import com.example.tfgapplibros.data.LibroPaginacion
import com.example.tfgapplibros.model.PrincipalVIewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun Principal(
    navController: NavHostController,
    viewModel: PrincipalVIewModel = viewModel()
) {
    BackHandler {
        //Esto es para que no se pueda volver para atras
    }
    val context = LocalContext.current
    val userId = Autentificacion.usuarioActualUid
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val currentUserId: String? = currentUser?.uid
    val libros by viewModel.libros.observeAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        Log.d("Librooooos",currentUserId?:"")
        viewModel.recogerLibros(currentUserId ?: "")
    }

    Scaffold(
        topBar = { TopBarPrincipal(navController,userId) }, content = { PaginaPrincipal(it,userId, libros, viewModel, navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPrincipal(
    navController: NavHostController,
    userId: String?
) {
//
//    val viewModel = viewModel<BusquedaViewModel>()
//    val searchText by viewModel.searchText.collectAsState()
//    val libro by viewModel.libro.collectAsState()
//    val isSearching by viewModel.isSearching.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Bookself") },
            actions = {
                var expanded by remember { mutableStateOf(false) }
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu opciones"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    Modifier.background(getColorFromResource(colorResId = R.color.background_dark))
                ) {
                    DropdownMenuItem(
                        text = { Text("Mi Perfil", color = Color.Black)},
                        onClick = {
                            expanded = false;
                            navController.navigate(PerfilScreen(userId = userId!!))})
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))
                    DropdownMenuItem(
                        text = { Text("Cerrar Sesion", color = Color.Black) },
                        onClick = {
                            expanded = false;
                            Autentificacion.logout { navController.navigate(LoginScreen) }})
                }
            }
        )
//        TextField(value = searchText,
//            onValueChange = viewModel::onSearchTextChange,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//                .shadow(
//                    elevation = 5.dp,
//                    spotColor = Color.DarkGray,
//                    shape = RoundedCornerShape(10.dp)
//                ),
//            placeholder = { Text(text = "Busqueda") }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        if (isSearching) {
//            Box(modifier = Modifier.fillMaxSize()){
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                items(libro) { Libro ->
//                    Text(
//                        text = "${Libro.titulo}",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp)
//                    )
//                }
//            }
//
//        }
    }
}

@Composable
fun PaginaPrincipal(
    it: PaddingValues,
    userId: String?,
    libros: List<LibroPaginacion>,
    viewModel: PrincipalVIewModel,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo}
            .map {
                it.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
            }
            .distinctUntilChanged()
            .collect { esFin ->
                if (esFin && !viewModel.loading && !viewModel.endOfListReached) {
                    viewModel.recogerLibros(userId ?: "")
                }
            }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(libros) { libroPag ->
            val libro = libroPag.libro
            CartaLibroPerfil(libro = libro) {
               navController.navigate(LibroScreen(userId = libro.userId, libroId = libro.libroId))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            if (viewModel.loading)
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp), color = getColorFromResource(
                        colorResId = R.color.secondary_dark
                    ))
                    
                }
        }
    }
}

@Composable
fun MyCard(title: String, author: String, image: ImageBitmap?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f / 16f)
                        .background(Color.LightGray)
                        .clip(shape = RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "Image",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = author,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
        }
    }
}

