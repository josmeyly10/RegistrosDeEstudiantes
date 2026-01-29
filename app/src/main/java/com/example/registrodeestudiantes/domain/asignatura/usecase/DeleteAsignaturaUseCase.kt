package com.example.registrodeestudiantes.domain.asignatura.usecase

import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}