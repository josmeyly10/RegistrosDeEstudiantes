package com.example.registrodeestudiantes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.registrodeestudiantes.presentacion.estudiante.EstudianteScreen
import com.example.registrodeestudiantes.ui.theme.RegistroDeEstudiantesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeEstudiantesTheme {
                EstudianteScreen()
            }
        }
    }
}