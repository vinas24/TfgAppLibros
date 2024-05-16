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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable

fun CampoTexto(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChanged(it) },
        label = { Text(label) },
        maxLines = 1,
        shape = MaterialTheme.shapes.small,
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CampoTextoLargo(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
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
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 30.dp),

        )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CampoContrasena(
    password: String,
    onPasswordChanged: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChanged(it) },
        label = { Text(label) },
        shape = MaterialTheme.shapes.small,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@Composable
fun CampoSlider(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    val listaEstados =
        listOf("Mucho uso", "Uso Moderado", "Poco Uso", "Como nuevo", "Nuevo")
    var estadoLibroTexto = listaEstados[(value - 1).toInt()]

    Column() {
        Text(text = "Estado:  $estadoLibroTexto", modifier = Modifier.padding(horizontal = 60.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 1f..5f,
            steps = 3,
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
        CampoContrasena(
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


