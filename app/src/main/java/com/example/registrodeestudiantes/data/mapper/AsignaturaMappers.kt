package com.example.registrodeestudiantes.data.mapper
import com.example.registrodeestudiantes.data.local.entities.AsignaturaEntity
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura

fun AsignaturaEntity.toAsignatura(): Asignatura {
    return Asignatura(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}

fun Asignatura.toEntity(): AsignaturaEntity {
    return AsignaturaEntity(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}