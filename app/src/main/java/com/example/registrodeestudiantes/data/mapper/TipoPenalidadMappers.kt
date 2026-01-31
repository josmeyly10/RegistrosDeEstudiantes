package com.example.registrodeestudiantes.data.mapper

import com.example.registrodeestudiantes.data.local.entities.TipoPenalidadEntity
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad

fun TipoPenalidadEntity.toTipoPenalidad(): TipoPenalidad {
    return TipoPenalidad(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}


fun TipoPenalidad.toEntity(): TipoPenalidadEntity {
    return TipoPenalidadEntity(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}