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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun Principal(
    navController: NavHostController
) {
    Scaffold(
        topBar = { TopBarPrincipal(navController) }, content = { PaginaPrincipal(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPrincipal(
    navController: NavHostController
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
            IconButton(onClick = {  navController.navigate("perfil")}) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
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
    it: PaddingValues
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