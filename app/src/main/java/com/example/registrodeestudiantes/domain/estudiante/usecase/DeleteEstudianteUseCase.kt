package com.example.registrodeestudiantes.domain.estudiante.usecase

import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}


