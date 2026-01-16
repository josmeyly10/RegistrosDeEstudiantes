package com.example.registrodeestudiantes.domain.estudiante.usecase
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import javax.inject.Inject


class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        return repository.getById(id)
    }
}