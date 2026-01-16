package com.example.registrodeestudiantes.presentacion.estudiante.edit
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditEstudianteScreen(
    state: EditEstudianteUiState,
    viewModel: EditEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EditEstudianteBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditEstudianteBody(
    state: EditEstudianteUiState,
    onEvent: (EditEstudianteUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(all = 16.dp)
        ) {

            OutlinedTextField(
                value = state.nombres,
                onValueChange = { onEvent(EditEstudianteUiEvent.NombresChanged(value = it)) },
                label = { Text(text = "Nombres") },
                isError = state.nombresError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombresError != null) {
                Text(
                    text = state.nombresError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(EditEstudianteUiEvent.EmailChanged(value = it)) },
                label = { Text(text = "Email") },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.edad,
                onValueChange = { onEvent(EditEstudianteUiEvent.EdadChanged(value = it)) },
                label = { Text(text = "Edad") },
                isError = state.edadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.edadError != null) {
                Text(
                    text = state.edadError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 16.dp))


            Row {

                Button(
                    onClick = { onEvent(EditEstudianteUiEvent.Save) },
                    enabled = !state.isSaving
                ) {
                    Text(text = "Guardar")
                }

                Spacer(Modifier.width(width = 8.dp))


                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditEstudianteUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) {
                        Text(text = "Eliminar")
                    }
                }
            }
        }
    }
}