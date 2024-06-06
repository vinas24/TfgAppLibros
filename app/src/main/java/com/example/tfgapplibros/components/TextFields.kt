package com.example.tfgapplibros.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfgapplibros.R

@Composable
fun CampoTexto(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    val colorPrim = getColorFromResource(colorResId = R.color.primary)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    val colorBack = getColorFromResource(colorResId = R.color.background_light)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorPrim2,
        backgroundColor = colorPrim2
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            label = { Text(label) },
            maxLines = 1,
            shape = MaterialTheme.shapes.small,
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = colorPrim2,
                focusedIndicatorColor = colorPrim,
                focusedContainerColor = colorBack,
                cursorColor = colorPrim,
                unfocusedIndicatorColor = colorPrim,
                unfocusedLabelColor = colorPrim,
                unfocusedContainerColor = colorBack,
                focusedTextColor = colorBack,
                unfocusedTextColor = colorPrim,
                focusedLeadingIconColor = colorPrim2,
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
    }
}

@Composable
fun CampoUsuarioLogin(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    val colorPrim = getColorFromResource(colorResId = R.color.primary)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    val colorBack = getColorFromResource(colorResId = R.color.background_light)
    val colorBack2 = getColorFromResource(colorResId = R.color.secondary)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorPrim2,
        backgroundColor = colorPrim2
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            label = { Text(label) },
            maxLines = 1,
            shape = MaterialTheme.shapes.small,
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = colorBack,
                focusedIndicatorColor = colorBack2,
                focusedContainerColor = colorBack,
                focusedTextColor = colorBack,
                cursorColor = colorPrim,
                focusedLeadingIconColor = colorPrim2,
                unfocusedIndicatorColor = colorPrim,
                unfocusedLabelColor = colorPrim,
                unfocusedContainerColor = colorBack,
                unfocusedTextColor = colorPrim,
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CampoTextoLargo(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val colorPrim = getColorFromResource(colorResId = R.color.primary)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    val colorBack = getColorFromResource(colorResId = R.color.background_light)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorPrim2,
        backgroundColor = colorPrim2
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            label = { Text(label) },
            shape = MaterialTheme.shapes.small,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default,
            ),
            colors = TextFieldDefaults.colors(
                focusedLabelColor = colorPrim2,
                focusedIndicatorColor = colorPrim2,
                focusedContainerColor = colorBack,
                cursorColor = colorPrim2,
                unfocusedIndicatorColor = colorPrim,
                unfocusedLabelColor = colorPrim,
                unfocusedContainerColor = colorBack,
                focusedTextColor = colorPrim2,
                unfocusedTextColor = colorPrim,
                focusedLeadingIconColor = colorPrim2,

                ),
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 30.dp),

            )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CampoContrasenaLogin(
    password: String,
    onPasswordChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val colorPrim = getColorFromResource(colorResId = R.color.primary)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    val colorBack = getColorFromResource(colorResId = R.color.background_light)
    val colorBack2 = getColorFromResource(colorResId = R.color.secondary)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorPrim2,
        backgroundColor = colorPrim2
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text(label) },
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = colorBack,
                focusedIndicatorColor = colorBack2,
                focusedContainerColor = colorBack,
                cursorColor = colorPrim2,
                unfocusedIndicatorColor = colorPrim,
                unfocusedLabelColor = colorPrim,
                unfocusedContainerColor = colorBack,
                focusedTextColor = colorPrim2,
                unfocusedTextColor = colorPrim,
                focusedLeadingIconColor = colorPrim2,

                ),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CampoContrasenaRegister(
    password: String,
    onPasswordChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val colorPrim = getColorFromResource(colorResId = R.color.primary)
    val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
    val colorBack = getColorFromResource(colorResId = R.color.background_light)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = colorPrim2,
        backgroundColor = colorPrim2
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text(label) },
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = colorPrim2,
                focusedIndicatorColor = colorPrim2,
                focusedContainerColor = colorBack,
                cursorColor = colorPrim2,
                unfocusedIndicatorColor = colorPrim,
                unfocusedLabelColor = colorPrim,
                unfocusedContainerColor = colorBack,
                focusedTextColor = colorPrim2,
                unfocusedTextColor = colorPrim,
                focusedLeadingIconColor = colorPrim2,

                ),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )

    }
}


@Composable
fun CampoSlider(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    val listaEstados =
        listOf("Mucho uso", "Uso Moderado", "Poco Uso", "Como nuevo", "Nuevo")
    var estadoLibroTexto = listaEstados[(value - 1).toInt()]

    val color = getColorFromResource(colorResId = R.color.primary_dark)
    Column() {
        Text(
            text = "Estado:  $estadoLibroTexto",
            modifier = Modifier.padding(horizontal = 60.dp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 1f..5f,
            steps = 3,
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color,
                inactiveTrackColor = getColorFromResource(colorResId = R.color.primary_muted)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
    }
}


@Composable
@Preview
fun VerCampos() {
    var usuario by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CampoTexto(
            text = usuario,
            onTextChanged = { usuario = it },
            label = "Nombre de Usuario",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        CampoContrasenaLogin(
            password = passwd,
            onPasswordChanged = { passwd = it },
            label = "Contrasena",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        CampoContrasenaRegister(
            password = passwd,
            onPasswordChanged = { passwd = it },
            label = "Contrasena",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
    }
}



