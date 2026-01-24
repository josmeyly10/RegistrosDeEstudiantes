package com.example.registrodeestudiantes.presentacion.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        DrawerItem(
            title = "Estudiantes",
            icon = Icons.Default.Person,
            route = Screen.EstudianteList
        ),
        DrawerItem(
            title = "Asignaturas",
            icon = Icons.Default.Book,
            route = Screen.AsignaturaList
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navHostController.navigate(item.route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    ) {
        content()
    }
}