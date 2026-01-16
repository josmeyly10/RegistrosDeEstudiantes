package com.example.registrodeestudiantes.domain.estudiante.repository

import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {

    suspend fun upsert(estudiante: Estudiante)
    suspend fun delete(id: Int)
    suspend fun getById(id: Int): Estudiante?
    suspend fun existsByNombres(nombres: String, excludeId: Int = 0): Boolean
    fun observeById(id: Int): Flow<Estudiante?>
    fun observeAll(): Flow<List<Estudiante>>
}