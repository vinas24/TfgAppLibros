package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfgapplibros.data.Libro
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.reflect.Modifier

@Composable
fun ImagenConLikeButton(libro: Libro) {
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
            onClick = { /*TODO cambiar el icon a vacio o viceversa y el like en firebase*/ },
            modifier = androidx.compose.ui.Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape
        ) {
            //TODO: cambiar icono por el del corazon
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = "Like",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}