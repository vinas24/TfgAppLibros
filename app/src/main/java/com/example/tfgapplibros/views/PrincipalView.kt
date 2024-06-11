package com.example.tfgapplibros.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfgapplibros.LoginScreen
import com.example.tfgapplibros.PerfilScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.model.Autentificacion
import com.google.firebase.auth.FirebaseAuth
import com.example.tfgapplibros.LibroScreen
import com.example.tfgapplibros.components.CartaLibroPerfil
import com.example.tfgapplibros.components.MySearchBar
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
    val userId = Autentificacion.usuarioActualUid
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val currentUserId: String? = currentUser?.uid
    val libros by viewModel.libros.observeAsState(initial = emptyList())
    val search by viewModel.search.observeAsState("")


    LaunchedEffect(Unit) {
        Log.d("Librooooos",currentUserId?:"")
        viewModel.recogerLibros(currentUserId ?: "")
    }

    Scaffold(
        topBar = { TopBarPrincipal(navController,userId, viewModel) }, content = { PaginaPrincipal(it,userId,search, libros, viewModel, navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPrincipal(
    navController: NavHostController,
    userId: String?,
    viewModel: PrincipalVIewModel,
) {
TopAppBar(
    modifier = Modifier.height(110.dp),
    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    title = {
        Row (modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()) {
                MySearchBar(placeholder = "Buscar...", Result = { viewModel.changesearch(it) })
            }

            },
    actions = {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            var expanded by remember { mutableStateOf(false) }
            IconButton(
                onClick = { expanded = true },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu opciones",
                    tint = Color.Gray
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                Modifier.background(getColorFromResource(colorResId = R.color.background))
            ) {
                DropdownMenuItem(
                    text = { Text("Mi Perfil", color = Color.Black) },
                    onClick = {
                        expanded = false;
                        navController.navigate(PerfilScreen(userId = userId!!))
                    })
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                DropdownMenuItem(
                    text = { Text("Cerrar Sesion", color = Color.Black) },
                    onClick = {
                        expanded = false;
                        Autentificacion.logout { navController.navigate(LoginScreen) }
                    })
            }
        }
    }
)
}

@Composable
fun PaginaPrincipal(
    it: PaddingValues,
    userId: String?,
    search: String,
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
        modifier = Modifier
            .fillMaxSize()
            .background(color = getColorFromResource(colorResId = R.color.background_light))
            .padding(top = 110.dp),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(libros) { libroPag ->
            val libro = libroPag.libro
            if(libro.autor.lowercase().contains(search.lowercase()) || libro.titulo.lowercase().contains(search.lowercase())) {
                CartaLibroPerfil(libro = libro) {
                    navController.navigate(
                        LibroScreen(
                            userId = libro.userId,
                            libroId = libro.libroId
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
        item {

            if (viewModel.loading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp), color = getColorFromResource(
                            colorResId = R.color.secondary_dark
                        )
                    )

                }
            }else {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(text = "Final del trayecto :)", color = Color.Black )
                }

            }
        }
    }
}


