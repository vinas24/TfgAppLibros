package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun RatingStar(
    estado: Int
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i<= estado) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Estado del libro: $estado / 5" ,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary)

        }
    }
}