package com.example.registrodeestudiantes.presentacion.tipopenalidad.list
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
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTipoPenalidadScreen(
    viewModel: ListTipoPenalidadViewModel = hiltViewModel(),
    onNavigateToEdit: (Int?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTipoPenalidadBody(
        state = state,
        onNavigateToEdit = onNavigateToEdit,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    onNavigateToEdit: (Int?) -> Unit,
    onEvent: (ListTipoPenalidadUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tipos de Penalidades") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(null) }
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
                onQueryChange = { onEvent(ListTipoPenalidadUiEvent.SearchQueryChanged(it)) }
            )


            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.tiposPenalidades.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.searchQuery.isBlank()) {
                            "No hay tipos de penalidades registradas"
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
                        items = state.tiposPenalidades,
                        key = { it.tipoId }
                    ) { tipoPenalidad ->
                        TipoPenalidadItem(
                            tipoPenalidad = tipoPenalidad,
                            onClick = { onNavigateToEdit(tipoPenalidad.tipoId) },
                            onDelete = { onEvent(ListTipoPenalidadUiEvent.OnDeleteTipoPenalidad(tipoPenalidad.tipoId)) }
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
        placeholder = { Text("Buscar por nombre...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        },
        singleLine = true
    )
}

@Composable
private fun TipoPenalidadItem(
    tipoPenalidad: TipoPenalidad,
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
                    text = tipoPenalidad.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = tipoPenalidad.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Puntos de Descuento: ${tipoPenalidad.puntosDescuento}",
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
private fun ListTipoPenalidadBodyPreview(){
    MaterialTheme {
        val state = ListTipoPenalidadUiState()
        ListTipoPenalidadBody(state, {}, {})
    }
}