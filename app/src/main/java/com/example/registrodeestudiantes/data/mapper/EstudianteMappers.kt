package com.example.registrodeestudiantes.data.mapper
import com.example.registrodeestudiantes.data.local.entities.EstudianteEntity
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante


fun EstudianteEntity.toEstudiante(): Estudiante {
    return Estudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}


fun Estudiante.toEntity(): EstudianteEntity {
    return EstudianteEntity(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}