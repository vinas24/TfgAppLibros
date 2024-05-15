package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage
import com.example.tfgapplibros.data.Libro


//TODO: Cambiar el modelo de la imagen al repositorio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartaLibroPerfil(
    libro: Libro,
    modifier: Modifier = Modifier,
    onclick: () -> Unit
) {
    ElevatedCard(
        onClick = onclick,
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = libro.imgUrl,
                contentDescription = "Libro",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .padding(bottom = 8.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 4.dp,
                            bottomEnd = 4.dp
                        )
                    )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.Start)) {
                Text(
                    text = libro.titulo,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }


        }
    }
}


