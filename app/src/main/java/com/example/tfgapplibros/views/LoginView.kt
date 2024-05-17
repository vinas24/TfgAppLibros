package com.example.tfgapplibros.views


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfgapplibros.PrincipalScreen
import com.example.tfgapplibros.R
import com.example.tfgapplibros.RegistroScreen
import com.example.tfgapplibros.components.CampoContrasena
import com.example.tfgapplibros.components.CampoTexto
import com.example.tfgapplibros.components.getColorFromResource
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.model.LoginViewModel


@Composable
fun LoginView(
    navController: NavHostController, viewModel: LoginViewModel = viewModel()
) {
    Scaffold {
        Login(it, navController, viewModel)
    }
}

@Composable
fun Login(
    it: PaddingValues, navController: NavHostController, viewModel: LoginViewModel
) {

    var usuario by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }
    val camposNoVacios = usuario.isNotEmpty() && passwd.isNotEmpty()
    //For error showcasing
    val context = LocalContext.current

    LaunchedEffect(viewModel.loginError) {
        if (viewModel.loginError != "") {
            Toast.makeText(context, viewModel.loginError, Toast.LENGTH_LONG).show()
            viewModel.resetLoginError()
        }
    }

    if (viewModel.loading) {
        Log.d("fdfe","ssssss")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .05f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator( color = getColorFromResource(colorResId = R.color.primary))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getColorFromResource(colorResId = R.color.background_light)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = "INICIAR SESION",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = getColorFromResource(colorResId = R.color.primary)
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        CampoTexto(
            text = usuario,
            onTextChanged = { usuario = it },
            label = "Nombre de Usuario",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        CampoContrasena(
            password = passwd,
            onPasswordChanged = { passwd = it },
            label = "Contrasena",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
        )


        Spacer(
            modifier = Modifier.size(16.dp)
        )
        val colorPrim = getColorFromResource(colorResId = R.color.primary_muted)
        val colorPrim2 = getColorFromResource(colorResId = R.color.primary_dark)
        Button(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            enabled = camposNoVacios && !viewModel.loading,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = colorPrim,
                containerColor = colorPrim2
            ),
            onClick = {
                viewModel
                    .signInConCorreoContrasena(
                        email = usuario,
                        passwd = passwd
                    ) {
                        navController.navigate(PrincipalScreen)
                    }
                usuario = ""
                passwd = ""

            }) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 6.dp,
                        horizontal = 24.dp),
                text = "Entrar",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "No tienes cuenta todavia?",
            color = Color.Black
        )
        Text(
            text = "Registrate",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(
                    onClick = { navController.navigate(RegistroScreen) }),
            color = getColorFromResource(colorResId = R.color.primary)
        )
    }
}