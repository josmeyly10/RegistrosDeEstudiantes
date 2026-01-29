package com.example.registrodeestudiantes.presentacion.asignatura
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
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.presentacion.asignatura.list.ListAsignaturaUiEvent
import com.example.registrodeestudiantes.presentacion.asignatura.list.ListAsignaturaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaScreen(
    viewModel: ListAsignaturaViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var asignaturaToEdit by remember { mutableStateOf<Asignatura?>(null) }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    asignaturaToEdit = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar asignatura")
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
                onQueryChange = { viewModel.onEvent(ListAsignaturaUiEvent
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
                state.asignaturas.isEmpty() -> {
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
                                    "No hay asignaturas registradas"
                                } else {
                                    "No se encontraron resultados"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (state.searchQuery.isBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Presiona el botón + para agregar una",
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
                            items = state.asignaturas,
                            key = { it.asignaturaId }
                        ) { asignatura ->
                            AsignaturaCard(
                                asignatura = asignatura,
                                onEdit = {
                                    asignaturaToEdit = asignatura
                                    showDialog = true
                                },
                                onDelete = {
                                    viewModel.onEvent(
                                        ListAsignaturaUiEvent.OnDeleteAsignatura(asignatura
                                            .asignaturaId)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }


        if (showDialog) {
            AsignaturaFormDialog(
                asignatura = asignaturaToEdit,
                onDismiss = {
                    showDialog = false
                    asignaturaToEdit = null
                },
                onSave = { asignatura ->
                    viewModel.onEvent(ListAsignaturaUiEvent.OnSaveAsignatura(asignatura))
                    showDialog = false
                    asignaturaToEdit = null
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
        placeholder = { Text("Buscar por nombre o código...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}


@Composable
private fun AsignaturaCard(
    asignatura: Asignatura,
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
                    text = asignatura.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Código: ${asignatura.codigo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${asignatura.creditos} créditos",
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
private fun AsignaturaFormDialog(
    asignatura: Asignatura?,
    onDismiss: () -> Unit,
    onSave: (Asignatura) -> Unit
) {
    var nombre by remember { mutableStateOf(asignatura?.nombre ?: "") }
    var codigo by remember { mutableStateOf(asignatura?.codigo ?: "") }
    var aula by remember { mutableStateOf(asignatura?.aula ?: "") }
    var creditos by remember { mutableStateOf(asignatura?.creditos?.toString() ?: "") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var codigoError by remember { mutableStateOf<String?>(null) }
    var aulaError by remember { mutableStateOf<String?>(null) }
    var creditosError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (asignatura == null) "Nueva Asignatura" else "Editar Asignatura",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = null
                    },
                    label = { Text("Nombre") },
                    isError = nombreError != null,
                    supportingText = nombreError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = {
                        codigo = it
                        codigoError = null
                    },
                    label = { Text("Código") },
                    isError = codigoError != null,
                    supportingText = codigoError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = aula,
                    onValueChange = {
                        aula = it
                        aulaError = null
                    },
                    label = { Text("Aula") },
                    isError = aulaError != null,
                    supportingText = aulaError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = creditos,
                    onValueChange = {
                        creditos = it
                        creditosError = null
                    },
                    label = { Text("Créditos") },
                    isError = creditosError != null,
                    supportingText = creditosError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {

                    var hasError = false

                    if (nombre.isBlank()) {
                        nombreError = "El nombre no puede estar vacío"
                        hasError = true
                    }

                    if (codigo.isBlank()) {
                        codigoError = "El código no puede estar vacío"
                        hasError = true
                    }

                    if (aula.isBlank()) {
                        aulaError = "El aula no puede estar vacío"
                        hasError = true
                    }

                    if (creditos.isBlank() || creditos.toIntOrNull() == null) {
                        creditosError = "Los créditos deben ser un número válido"
                        hasError = true
                    }

                    if (!hasError) {
                        val id = if (asignatura == null || asignatura.asignaturaId == 0) {
                            0
                        } else {
                            asignatura.asignaturaId
                        }

                        onSave(
                            Asignatura(
                                asignaturaId = id,
                                nombre = nombre.trim(),
                                codigo = codigo.trim(),
                                aula = aula.trim(),
                                creditos = creditos.toInt()
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
