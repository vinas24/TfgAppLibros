package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfgapplibros.R
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion
import kotlin.math.sin

@Composable
fun ImagenConLikeButton(libro: Libro, userId: String, liked: Boolean, onLike: (Libro) -> Unit) {
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
        if (libro.userId != Autentificacion.usuarioActualUid) {
            FloatingActionButton(
                onClick = { onLike(libro) },
                modifier = androidx.compose.ui.Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = CircleShape,
                containerColor = getColorFromResource(colorResId = R.color.primary_muted)
            ) {
                if (liked) {
                    Icon(
                        modifier = Modifier.scale(1.4f),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = getColorFromResource(colorResId = R.color.primary))
                }else{
                        Icon(
                            modifier = Modifier.scale(1.4f),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = getColorFromResource(colorResId = R.color.primary))
                }
            }
        }

    }
}