package com.example.registrodeestudiantes.domain.asignatura.usecase
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(id: Int): Flow<Asignatura?> {
        return repository.observeById(id)
    }
}