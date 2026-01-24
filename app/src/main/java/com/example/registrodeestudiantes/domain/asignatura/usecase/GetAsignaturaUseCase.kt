package com.example.registrodeestudiantes.domain.asignatura.usecase
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import javax.inject.Inject


class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? {
        return repository.getById(id)
    }
}