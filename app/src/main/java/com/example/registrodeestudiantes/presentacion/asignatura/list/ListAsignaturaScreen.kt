package com.example.registrodeestudiantes.presentacion.asignatura.list
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAsignaturaScreen(
    viewModel: ListAsignaturaViewModel = hiltViewModel(),
    onNavigateToEdit: (Int?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListAsignaturaBody(
        state = state,
        onNavigateToEdit = onNavigateToEdit,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListAsignaturaBody(
    state: ListAsignaturaUiState,
    onNavigateToEdit: (Int?) -> Unit,
    onEvent: (ListAsignaturaUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Asignaturas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(null) }
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
                onQueryChange = { onEvent(ListAsignaturaUiEvent.SearchQueryChanged(it)) }
            )


            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.asignaturas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.searchQuery.isBlank()) {
                            "No hay asignaturas registradas"
                        } else {
                            "No se encontraron resultados"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.asignaturas,
                        key = { it.asignaturaId }
                    ) { asignatura ->
                        AsignaturaItem(
                            asignatura = asignatura,
                            onClick = { onNavigateToEdit(asignatura.asignaturaId) },
                            onDelete = { onEvent(ListAsignaturaUiEvent.OnDeleteAsignatura
                                (asignatura.asignaturaId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar por código o nombre...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        },
        singleLine = true
    )
}

@Composable
private fun AsignaturaItem(
    asignatura: Asignatura,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Código: ${asignatura.codigo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Aula: ${asignatura.aula} | Créditos: ${asignatura.creditos}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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

@Preview
@Composable
private fun ListAsignaturaBodyPreview(){
    MaterialTheme {
        val state = ListAsignaturaUiState()
        ListAsignaturaBody(state, {}, {})
    }
}