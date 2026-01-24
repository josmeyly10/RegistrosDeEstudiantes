package com.example.registrodeestudiantes.presentacion.asignatura.edit
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditAsignaturaScreen(
    state: EditAsignaturaUiState,
    viewModel: EditAsignaturaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EditAsignaturaBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditAsignaturaBody(
    state: EditAsignaturaUiState,
    onEvent: (EditAsignaturaUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(all = 16.dp)
        ) {


            OutlinedTextField(
                value = state.codigo,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CodigoChanged(value = it)) },
                label = { Text(text = "Código") },
                isError = state.codigoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.codigoError != null) {
                Text(
                    text = state.codigoError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditAsignaturaUiEvent.NombreChanged(value = it)) },
                label = { Text(text = "Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    text = state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.aula,
                onValueChange = { onEvent(EditAsignaturaUiEvent.AulaChanged(value = it)) },
                label = { Text(text = "Aula") },
                isError = state.aulaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.aulaError != null) {
                Text(
                    text = state.aulaError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.creditos,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CreditosChanged(value = it)) },
                label = { Text(text = "Créditos") },
                isError = state.creditosError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.creditosError != null) {
                Text(
                    text = state.creditosError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 16.dp))

            Row {

                Button(
                    onClick = { onEvent(EditAsignaturaUiEvent.Save) },
                    enabled = !state.isSaving
                ) {
                    Text(text = "Guardar")
                }

                Spacer(Modifier.width(width = 8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditAsignaturaUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) {
                        Text(text = "Eliminar")
                    }
                }
            }
        }
    }
}
