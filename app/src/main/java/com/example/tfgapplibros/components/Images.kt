package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfgapplibros.R
import com.example.tfgapplibros.data.Libro

@Composable
fun ImagenConLikeButton(libro: Libro, onLike: (Libro) -> Unit) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .padding(bottom = 16.dp)
    ) {
        AsyncImage(
            model = libro.imgUrl,
            contentDescription = "Libro",
            contentScale = ContentScale.Crop,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
        )
        FloatingActionButton(
            onClick = { onLike(libro) },
            modifier = androidx.compose.ui.Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = getColorFromResource(colorResId = R.color.primary_muted)
        ) {
            //TODO: cambiar icono por el del corazon y que cambie de relleno a vacio
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = "Like",
                tint = getColorFromResource(colorResId = R.color.primary)
            )
        }

    }
}