package com.example.registrodeestudiantes.presentacion.tipopenalidad.edit

data class EditTipoPenalidadUiState(
    val tipoId: Int? = null,

    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: String = "",

    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosDescuentoError: String? = null,

    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,

    val saved: Boolean = false,
    val deleted: Boolean = false
)