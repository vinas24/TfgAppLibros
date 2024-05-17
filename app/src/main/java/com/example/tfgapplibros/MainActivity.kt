package com.example.tfgapplibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.model.Autentificacion
import com.example.tfgapplibros.ui.theme.TfgAppLibrosTheme
import com.example.tfgapplibros.views.AddLibro
import com.example.tfgapplibros.views.LibroView
import com.example.tfgapplibros.views.LoginView
import com.example.tfgapplibros.views.Perfil
import com.example.tfgapplibros.views.Principal
import com.example.tfgapplibros.views.RegisterView
import com.google.firebase.FirebaseApp
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfgAppLibrosTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination =
                    if (Autentificacion.checkUsuarioGuardado()) {
                        PrincipalScreen
                    } else {
                        LoginScreen

                    }
                ) {
                    composable<LoginScreen> {
                        LoginView(navController = navController)
                    }
                    composable<RegistroScreen> {
                        RegisterView(navController = navController)
                    }
                    composable<PrincipalScreen> {
                        Principal(navController = navController)
                    }
                    composable<PerfilScreen> {
                        val args = it.toRoute<PerfilScreen>()
                        Perfil(navController = navController, userId = args.userId)
                    }
                    composable<AddLibroScreen> {
                        val args = it.toRoute<AddLibroScreen>()
                        AddLibro(
                            userId = args.userId,
                            navController = navController,
                            libroId = args.libroId)
                    }
                    composable<LibroScreen> {
                        val args = it.toRoute<LibroScreen>()
                        LibroView(
                            navController = navController,
                            userId = args.userId,
                            libroId = args.libroId
                        )
                    }
                }
            }
        }
    }
}

@Serializable
object LoginScreen

@Serializable
object RegistroScreen

@Serializable
object PrincipalScreen

@Serializable
data class PerfilScreen(
    val userId: String
)

@Serializable
data class AddLibroScreen(
    val userId: String,
    val libroId: String? = null
)

@Serializable
data class LibroScreen(
    val userId: String,
    val libroId: String
)


