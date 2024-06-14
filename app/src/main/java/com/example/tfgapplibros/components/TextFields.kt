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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfgapplibros.R
import com.google.firebase.database.Query
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        handleColor = colorPrim,
        backgroundColor = Color.LightGray
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
                focusedTextColor = colorPrim,
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
        handleColor = colorPrim,
        backgroundColor = Color.LightGray
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
                focusedTextColor = colorPrim,
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
        handleColor = colorPrim,
        backgroundColor = Color.LightGray
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
        handleColor = colorPrim,
        backgroundColor = Color.LightGray
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
                .onFocusChanged { !it.isFocused }
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
        backgroundColor = Color.Gray
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
                .onFocusChanged { !it.isFocused },

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
                .onFocusChanged { !it.isFocused }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    Result: (String) -> Unit = {}
) {
    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val customTextSelectionColors = TextSelectionColors(
        handleColor = getColorFromResource(colorResId = R.color.primary),
        backgroundColor = Color.LightGray
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
    OutlinedTextField(
        value = searchTextState.value,
        onValueChange = { value ->
            searchTextState.value = value
            if (value.text.isBlank()) {
                Result(searchTextState.value.text)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = CircleShape)
            .padding(4.dp)
            .onFocusChanged { !it.isFocused },
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            IconButton(onClick = { Result(searchTextState.value.text)}) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            }

        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { Result(searchTextState.value.text)}),
        shape = CircleShape,
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = getColorFromResource(colorResId = R.color.primary),
           cursorColor = getColorFromResource(colorResId = R.color.primary),
            focusedTextColor = Color.Gray
        )
    )
    }
}


