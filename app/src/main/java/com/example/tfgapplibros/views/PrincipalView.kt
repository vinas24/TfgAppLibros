package com.example.tfgapplibros.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tfgapplibros.LoginScreen
import com.example.tfgapplibros.PerfilScreen
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion

@Composable
fun Principal(
    navController: NavHostController
) {
    val userId = Autentificacion.usuarioActualUid
    Scaffold(
        topBar = { TopBarPrincipal(navController,userId) }, content = { PaginaPrincipal(it,userId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPrincipal(
    navController: NavHostController,
    userId: String?
) {
    TopAppBar(
        title = {
            BasicTextField(
                value = TextFieldValue(),
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),

                )
        },
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
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Mi Perfil") },
                    onClick = {navController.navigate(PerfilScreen(userId = userId!!))})
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                DropdownMenuItem(
                    text = { Text("Cerrar Sesion") },
                    onClick = {Autentificacion.logout { navController.navigate(LoginScreen) }})
            }
        },
        modifier = Modifier
            .padding(10.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
    )
}

@Composable
fun PaginaPrincipal(
    it: PaddingValues,
    userId: String?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(10) { index ->
            MyCard("titulo","Autor", null)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MyCard(title: String, author: String, image: ImageBitmap?) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
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

