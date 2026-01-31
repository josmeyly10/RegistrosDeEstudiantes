package com.example.registrodeestudiantes.presentacion.tipopenalidad.edit
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditTipoPenalidadScreen(
    viewModel: EditTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EditTipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditTipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(all = 16.dp)
        ) {


            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(value = it)) },
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
                value = state.descripcion,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(value = it)) },
                label = { Text(text = "Descripción") },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.descripcionError != null) {
                Text(
                    text = state.descripcionError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.puntosDescuento,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosDescuentoChanged(value = it)) },
                label = { Text(text = "Puntos de Descuento") },
                isError = state.puntosDescuentoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.puntosDescuentoError != null) {
                Text(
                    text = state.puntosDescuentoError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 16.dp))

            Row {

                Button(
                    onClick = { onEvent(EditTipoPenalidadUiEvent.Save) },
                    enabled = !state.isSaving
                ) {
                    Text(text = "Guardar")
                }

                Spacer(Modifier.width(width = 8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditTipoPenalidadUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) {
                        Text(text = "Eliminar")
                    }
                }
            }


        }


    }
}

@Preview
@Composable
private fun EditTipoPenalidadBodyPreview() {
    val state = EditTipoPenalidadUiState()
    MaterialTheme {
        EditTipoPenalidadBody(state = state, onEvent = {})
    }
}