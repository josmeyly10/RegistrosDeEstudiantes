package com.example.registrodeestudiantes.data.repository
import com.example.registrodeestudiantes.data.local.dao.EstudianteDao
import com.example.registrodeestudiantes.data.mapper.toEntity
import com.example.registrodeestudiantes.data.mapper.toEstudiante
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val dao: EstudianteDao
) : EstudianteRepository {

    override suspend fun upsert(estudiante: Estudiante) {
        dao.upsert(estudiante.toEntity())
    }

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }

    override suspend fun getById(id: Int): Estudiante? {
        return dao.getById(id)?.toEstudiante()
    }

    override suspend fun existsByNombres(nombres: String, excludeId: Int): Boolean {
        return dao.existsByNombres(nombres, excludeId)
    }

    override fun observeById(id: Int): Flow<Estudiante?> {
        return dao.observeById(id).map { it?.toEstudiante() }
    }

    override fun observeAll(): Flow<List<Estudiante>> {
        return dao.observeAll().map { list ->
            list.map { it.toEstudiante() }
        }
    }
}