package com.example.registrodeestudiantes.presentacion.estudiante
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.presentacion.estudiante.list.ListEstudianteUiEvent
import com.example.registrodeestudiantes.presentacion.estudiante.list.ListEstudianteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteScreen(
    viewModel: ListEstudianteViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var estudianteToEdit by remember { mutableStateOf<Estudiante?>(null) }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    estudianteToEdit = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar estudiante")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            SearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onEvent(ListEstudianteUiEvent
                    .SearchQueryChanged(it)) },
                modifier = Modifier.padding(16.dp)
            )


            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.estudiantes.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (state.searchQuery.isBlank()) {
                                    "No hay estudiantes registrados"
                                } else {
                                    "No se encontraron resultados"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (state.searchQuery.isBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Presiona el botón + para agregar uno",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.estudiantes,
                            key = { it.estudianteId }
                        ) { estudiante ->
                            EstudianteCard(
                                estudiante = estudiante,
                                onEdit = {
                                    estudianteToEdit = estudiante
                                    showDialog = true
                                },
                                onDelete = {
                                    viewModel.onEvent(
                                        ListEstudianteUiEvent.OnDeleteEstudiante(estudiante
                                            .estudianteId)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }


        if (showDialog) {
            EstudianteFormDialog(
                estudiante = estudianteToEdit,
                onDismiss = {
                    showDialog = false
                    estudianteToEdit = null
                },
                onSave = { estudiante ->
                    viewModel.onEvent(ListEstudianteUiEvent.OnSaveEstudiante(estudiante))
                    showDialog = false
                    estudianteToEdit = null
                }
            )
        }
    }
}


@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Buscar por nombre o email...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}


@Composable
private fun EstudianteCard(
    estudiante: Estudiante,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = estudiante.nombres,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = estudiante.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${estudiante.edad} años",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}


@Composable
private fun EstudianteFormDialog(
    estudiante: Estudiante?,
    onDismiss: () -> Unit,
    onSave: (Estudiante) -> Unit
) {
    var nombres by remember { mutableStateOf(estudiante?.nombres ?: "") }
    var email by remember { mutableStateOf(estudiante?.email ?: "") }
    var edad by remember { mutableStateOf(estudiante?.edad?.toString() ?: "") }

    var nombresError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var edadError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (estudiante == null) "Nuevo Estudiante" else "Editar Estudiante",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = nombres,
                    onValueChange = {
                        nombres = it
                        nombresError = null
                    },
                    label = { Text("Nombres") },
                    isError = nombresError != null,
                    supportingText = nombresError?.let { { Text(it, color = MaterialTheme
                        .colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Email") },
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it, color = MaterialTheme
                        .colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


                OutlinedTextField(
                    value = edad,
                    onValueChange = {
                        edad = it
                        edadError = null
                    },
                    label = { Text("Edad") },
                    isError = edadError != null,
                    supportingText = edadError?.let { { Text(it, color = MaterialTheme.
                    colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    var hasError = false

                    if (nombres.isBlank()) {
                        nombresError = "Los nombres no pueden estar vacíos"
                        hasError = true
                    }
                    if (email.isBlank() || !email.contains("@")) {
                        emailError = "El email debe contener @"
                        hasError = true
                    }
                    if (edad.isBlank() || edad.toIntOrNull() == null) {
                        edadError = "La edad debe ser un número válido"
                        hasError = true
                    }

                    if (!hasError) {
                        onSave(
                            Estudiante(
                                estudianteId = estudiante?.estudianteId ?: 0,
                                nombres = nombres.trim(),
                                email = email.trim(),
                                edad = edad.toInt()
                            )
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}