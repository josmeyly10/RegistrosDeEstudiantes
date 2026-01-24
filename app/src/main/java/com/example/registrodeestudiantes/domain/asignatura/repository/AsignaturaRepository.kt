package com.example.registrodeestudiantes.domain.asignatura.repository
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import kotlinx.coroutines.flow.Flow


interface AsignaturaRepository {
    suspend fun upsert(asignatura: Asignatura)
    suspend fun delete(id: Int)
    suspend fun getById(id: Int): Asignatura?
    suspend fun existsByCodigo(codigo: String, excludeId: Int = 0): Boolean
    suspend fun existsByNombre(nombre: String, excludeId: Int = 0): Boolean
    fun observeById(id: Int): Flow<Asignatura?>
    fun observeAll(): Flow<List<Asignatura>>
}