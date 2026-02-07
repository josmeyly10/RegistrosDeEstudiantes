package com.example.registrodeestudiantes.presentacion.tipopenalidad
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.presentacion.tipopenalidad.list.ListTipoPenalidadUiEvent
import com.example.registrodeestudiantes.presentacion.tipopenalidad.list.ListTipoPenalidadViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoPenalidadScreen(
    viewModel: ListTipoPenalidadViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var tipoPenalidadToEdit by remember { mutableStateOf<TipoPenalidad?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tipoPenalidadToEdit = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tipo de penalidad")
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
                onQueryChange = { viewModel.onEvent(ListTipoPenalidadUiEvent
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
                state.tiposPenalidades.isEmpty() -> {
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
                                    "No hay tipos de penalidades registradas"
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
                            items = state.tiposPenalidades,
                            key = { it.tipoId }
                        ) { tipoPenalidad ->
                            TipoPenalidadCard(
                                tipoPenalidad = tipoPenalidad,
                                onEdit = {
                                    tipoPenalidadToEdit = tipoPenalidad
                                    showDialog = true
                                },
                                onDelete = {
                                    viewModel.onEvent(
                                        ListTipoPenalidadUiEvent.OnDeleteTipoPenalidad(tipoPenalidad.tipoId)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }


        if (showDialog) {
            TipoPenalidadFormDialog(
                tipoPenalidad = tipoPenalidadToEdit,
                onDismiss = {
                    showDialog = false
                    tipoPenalidadToEdit = null
                },
                onSave = { tipoPenalidad ->
                    viewModel.onEvent(ListTipoPenalidadUiEvent.OnSaveTipoPenalidad(tipoPenalidad))
                    showDialog = false
                    tipoPenalidadToEdit = null
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
        placeholder = { Text("Buscar por nombre...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}


@Composable
private fun TipoPenalidadCard(
    tipoPenalidad: TipoPenalidad,
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
                    text = tipoPenalidad.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = tipoPenalidad.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${tipoPenalidad.puntosDescuento} puntos de descuento",
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
private fun TipoPenalidadFormDialog(
    tipoPenalidad: TipoPenalidad?,
    onDismiss: () -> Unit,
    onSave: (TipoPenalidad) -> Unit
) {
    var nombre by remember { mutableStateOf(tipoPenalidad?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(tipoPenalidad?.descripcion ?: "") }
    var puntosDescuento by remember { mutableStateOf(tipoPenalidad?.puntosDescuento?.toString() ?: "") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var descripcionError by remember { mutableStateOf<String?>(null) }
    var puntosDescuentoError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (tipoPenalidad == null) "Nuevo Tipo de Penalidad" else "Editar Tipo de Penalidad",
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
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                        descripcionError = null
                    },
                    label = { Text("Descripción") },
                    isError = descripcionError != null,
                    supportingText = descripcionError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = puntosDescuento,
                    onValueChange = {
                        puntosDescuento = it
                        puntosDescuentoError = null
                    },
                    label = { Text("Puntos de Descuento") },
                    isError = puntosDescuentoError != null,
                    supportingText = puntosDescuentoError?.let {
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

                    if (descripcion.isBlank()) {
                        descripcionError = "La descripción no puede estar vacía"
                        hasError = true
                    }

                    val puntos = puntosDescuento.toIntOrNull()
                    if (puntos == null) {
                        puntosDescuentoError = "Debe ingresar un número válido"
                        hasError = true
                    } else if (puntos <= 0) {
                        puntosDescuentoError = "Los puntos deben ser mayor a cero"
                        hasError = true
                    }

                    if (!hasError) {
                        val id = if (tipoPenalidad == null || tipoPenalidad.tipoId == 0) {
                            0
                        } else {
                            tipoPenalidad.tipoId
                        }

                        onSave(
                            TipoPenalidad(
                                tipoId = id,
                                nombre = nombre.trim(),
                                descripcion = descripcion.trim(),
                                puntosDescuento = puntosDescuento.toInt()
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