package com.example.registrodeestudiantes.data.repository
import com.example.registrodeestudiantes.data.local.dao.AsignaturaDao
import com.example.registrodeestudiantes.data.mapper.toAsignatura
import com.example.registrodeestudiantes.data.mapper.toEntity
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {

    override suspend fun upsert(asignatura: Asignatura) {
        dao.upsert(asignatura.toEntity())
    }

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }

    override suspend fun getById(id: Int): Asignatura? {
        return dao.getById(id)?.toAsignatura()
    }

    override suspend fun existsByCodigo(codigo: String, excludeId: Int): Boolean {
        return dao.existsByCodigo(codigo, excludeId)
    }

    override suspend fun existsByNombre(nombre: String, excludeId: Int): Boolean {
        return dao.existsByNombre(nombre, excludeId)
    }

    override fun observeById(id: Int): Flow<Asignatura?> {
        return dao.observeById(id).map { it?.toAsignatura() }
    }

    override fun observeAll(): Flow<List<Asignatura>> {
        return dao.observeAll().map { list ->
            list.map { it.toAsignatura() }
        }
    }
}