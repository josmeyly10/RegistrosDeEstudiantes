package com.example.registrodeestudiantes.data.repository
import com.example.registrodeestudiantes.data.local.dao.TipoPenalidadDao
import com.example.registrodeestudiantes.data.mapper.toEntity
import com.example.registrodeestudiantes.data.mapper.toTipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {

    override suspend fun upsert(tipoPenalidad: TipoPenalidad) {
        dao.upsert(tipoPenalidad.toEntity())
    }

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }

    override suspend fun getById(id: Int): TipoPenalidad? {
        return dao.getById(id)?.toTipoPenalidad()
    }

    override suspend fun existsByNombre(nombre: String, excludeId: Int): Boolean {
        return dao.existsByNombre(nombre, excludeId)
    }

    override fun observeById(id: Int): Flow<TipoPenalidad?> {
        return dao.observeById(id).map { it?.toTipoPenalidad() }
    }

    override fun observeAll(): Flow<List<TipoPenalidad>> {
        return dao.observeAll().map { list ->
            list.map { it.toTipoPenalidad() }
        }
    }
}