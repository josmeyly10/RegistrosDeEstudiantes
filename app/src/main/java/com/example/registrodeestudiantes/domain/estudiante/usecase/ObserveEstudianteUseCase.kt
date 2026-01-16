package com.example.registrodeestudiantes.domain.estudiante.usecase
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(id: Int): Flow<Estudiante?> {
        return repository.observeById(id)
    }
}

