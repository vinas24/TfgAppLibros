package com.example.tfgapplibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfgapplibros.ui.theme.TfgAppLibrosTheme
import com.example.tfgapplibros.views.AddLibro
import com.example.tfgapplibros.views.LibroView
import com.example.tfgapplibros.views.LoginView
import com.example.tfgapplibros.views.Perfil
import com.example.tfgapplibros.views.Principal
import com.example.tfgapplibros.views.RegisterView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TfgAppLibrosTheme {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginView(navController)
                        }
                        composable("registro") {
                            RegisterView(navController)
                        }
                        composable("principal") {
                            Principal(navController)
                        }
                        composable("perfil") {
                            Perfil()
                        }
                        composable("libro") {
                            LibroView()
                        }
                        composable("addlibro") {
                            AddLibro()
                        }

                }
            }
        }
    }
}
