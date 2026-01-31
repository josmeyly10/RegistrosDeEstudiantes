package com.example.registrodeestudiantes.domain.tipopenalidad.repository
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    suspend fun upsert(tipoPenalidad: TipoPenalidad)
    suspend fun delete(id: Int)
    suspend fun getById(id: Int): TipoPenalidad?
    suspend fun existsByNombre(nombre: String, excludeId: Int = 0): Boolean
    fun observeById(id: Int): Flow<TipoPenalidad?>
    fun observeAll(): Flow<List<TipoPenalidad>>
}