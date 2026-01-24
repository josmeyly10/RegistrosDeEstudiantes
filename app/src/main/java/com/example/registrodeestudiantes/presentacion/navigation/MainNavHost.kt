package com.example.registrodeestudiantes.presentacion.navigation
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.registrodeestudiantes.presentacion.asignatura.AsignaturaScreen
import com.example.registrodeestudiantes.presentacion.estudiante.EstudianteScreen
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                EstudianteScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Screen.AsignaturaList> {
                AsignaturaScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        }
    }
}