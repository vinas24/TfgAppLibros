package com.example.tfgapplibros.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tfgapplibros.R

@Composable
fun BotonNormal(texto: String, enabled: Boolean, onClick: () -> Unit) {
    val colorPrimMut = getColorFromResource(colorResId = R.color.primary_muted)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    Button(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = colorPrimMut,
            containerColor = colorPrim2,
        ),
        onClick = onClick) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 24.dp),
            text = texto,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}