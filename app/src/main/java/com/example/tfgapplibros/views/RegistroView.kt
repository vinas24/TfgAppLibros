package com.example.tfgapplibros.views
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tfgapplibros.PrincipalScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.components.BotonNormal
import com.example.tfgapplibros.components.BotonRegister
import com.example.tfgapplibros.components.CampoContrasenaRegister
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.model.RegisterViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUser (
    navController : NavHostController,
    viewModel : RegisterViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Nuevo lector", color = Color.White)  },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = getColorFromResource(colorResId = R.color.background_light),
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.primary_dark),
                    titleContentColor = Color.White))
        },
        containerColor = getColorFromResource(colorResId = R.color.background_light)
    ) {
        RegistrarUsuarioView(
            viewModel = viewModel,
            navController = navController)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarUsuarioView(
    viewModel : RegisterViewModel,
    navController : NavHostController
) {
    var listaGeneros = listOf("Ficción", "Fantasía", "Misterio", "Romance", "Histórica", "Poesía", "Infantil", "Juveníl", "Autoayuda", "Biográfica" )

    val context = LocalContext.current

    val nombre by viewModel.nombre.observeAsState("")
    val apellidos by viewModel.apellidos.observeAsState("")
    val nombreUsuario by viewModel.nombreUsuario.observeAsState("")
    val contrasena by viewModel.contrasena.observeAsState("")
    val edad by viewModel.edad.observeAsState("")
    val correo by viewModel.correo.observeAsState("")
    val direccion by viewModel.direccion.observeAsState("")
    val ciudad by viewModel.ciudad.observeAsState("")
    val pais by viewModel.pais.observeAsState("")
    val codigoPostal by viewModel.codigoPostal.observeAsState("")
    val numeroTelefono by viewModel.numeroTelefono.observeAsState("")
    val fotoPerfil by viewModel.fotoPerfil.observeAsState()

    //Dropdown
    val generos by viewModel.generos.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }

    //Imagen
    val placeholderId = R.drawable.libro
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { viewModel.fotoPerfilChange(uri) } }
    )
    val camposRellenos =
        nombre.isNotEmpty() && apellidos.isNotEmpty() && nombreUsuario.isNotEmpty() && contrasena.isNotEmpty() && edad != 0
                && correo.isNotBlank() && direccion.isNotEmpty() && pais.isNotEmpty() && ciudad.isNotEmpty() && codigoPostal != 0
                && numeroTelefono != 0

    val commonModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)

    if (viewModel.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .05f))
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = getColorFromResource(colorResId = R.color.primary))
        }
    }

    Surface(
        color = getColorFromResource(colorResId = R.color.background_light),
        modifier = Modifier.padding(top = 95.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            Spacer(modifier = Modifier.height(32.dp))
            CampoTexto(
                text = nombre,
                onTextChanged = { viewModel.nombreChange(it) },
                label = "Nombre",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = apellidos,
                onTextChanged = { viewModel.apellidosChange(it) },
                label = "Apellidos",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = nombreUsuario,
                onTextChanged = { viewModel.nombreUsuarioChange(it) },
                label = "Nombre de Usuario",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = correo,
                onTextChanged = { viewModel.correoChange(it) },
                label = "Correo",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoContrasenaRegister(
                password = contrasena,
                onPasswordChanged = { viewModel.contrasenaChange(it) },
                label = "Contraseña",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            //TODO: cambiar a tipo string y que el check se haga en el VM
            CampoTexto(
                text = edad.toString(),
                onTextChanged = { viewModel.edadChange(it.toInt()) },
                label = "Edad",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = direccion,
                onTextChanged = { viewModel.direccionChange(it) },
                label = "Direccion",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = ciudad,
                onTextChanged = { viewModel.ciudadChange(it) },
                label = "Ciudad",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = pais,
                onTextChanged = { viewModel.paisChange(it) },
                label = "País",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = codigoPostal.toString(),
                onTextChanged = { viewModel.codigoPostalChange(it.toInt()) },
                label = "Código postal",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                text = numeroTelefono.toString(),
                onTextChanged = { viewModel.numeroTelefonoChange(it.trim().toInt()) },
                label = "Número teléfono",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = commonModifier
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.padding(horizontal = 60.dp)
            ) {
                val colorPrim = getColorFromResource(colorResId = R.color.primary)
                val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
                val colorBack = getColorFromResource(colorResId = R.color.background_light)

                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = generos.joinToString(", "),
                    onValueChange = { },
                    label = { Text("Géneros favoritos") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
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
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    Modifier.background(getColorFromResource(colorResId = R.color.background_dark))
                ) {
                    listaGeneros.forEach { selec ->
                        DropdownMenuItem(
                            text = { Text(text = selec, color = Color.Black) },
                            onClick = {
                                if (selec !in generos) {
                                    val generosFavoritos = generos + selec
                                    viewModel.generosChange(
                                        generosFavoritos
                                    )
                                }
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            if (fotoPerfil != null) {
                AsyncImage(
                    model = fotoPerfil,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .aspectRatio(4f / 5f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = placeholderId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .aspectRatio(4f / 5f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        ),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                BotonRegister(
                    texto = "Crear Usuario",
                    enabled = camposRellenos
                )
                {
                    viewModel.registerUser() {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                        navController.navigate(
                            PrincipalScreen
                        )
                    }
                }
            }
        }
    }
}

