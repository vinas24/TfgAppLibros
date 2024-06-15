package com.example.tfgapplibros.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.tfgapplibros.R
import kotlin.math.abs

@Composable
fun RatingStar(
    estado: Int
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= estado) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Estado del libro: $estado / 5",
                modifier = Modifier.size(24.dp),
                tint = getColorFromResource(colorResId = R.color.primary)
            )

        }
    }
}

@Composable
fun TextoCarta(tit: String, aut: String, genero: String) {
        val texto = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = getColorFromResource(colorResId = R.color.primary),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
            ) {
                append(tit.acortarTxt(15))
            }
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                )
            ) {
                append(", ${aut.acortarTxt(15 )}")
            }
        }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start

    ) {
        Text(text = texto)
        CajaGenero(nombre = genero, fontsize = MaterialTheme.typography.labelSmall.fontSize)
    }
}

@Preview
@Composable
fun Prev() {
    Column {
        RatingStar(estado = 3)
        TextoCarta(tit = "Titulo", aut = "Autor", genero = "genero")
    }

}


fun String.toColor(): Color {
    val hash = this.hashCode()

    // Generate RGB values from hash, capping saturation
    val red = abs(hash % 256)
    val green = abs((hash / 256) % 256)
    val blue = abs((hash / (256 * 256)) % 256)

    // Reduce saturation by blending with white
    val cappedRed = (red + 255) / 2
    val cappedGreen = (green + 255) / 2
    val cappedBlue = (blue + 255) / 2

    return Color(cappedRed, cappedGreen, cappedBlue)
}

fun String.acortarTxt( chars: Int): String {
    return if (this.length <= chars){
        this
    } else
        "${this.take(chars)}..."
}
@Composable
fun getColorFromResource(colorResId: Int): Color {
    val context = LocalContext.current
    return Color(ContextCompat.getColor(context, colorResId))
}



fun openEmailDialog(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, "Bookself: Intercambio de libro")
    }
    context.startActivity(intent)
}
