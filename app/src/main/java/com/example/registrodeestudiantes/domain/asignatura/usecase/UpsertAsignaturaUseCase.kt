package com.example.registrodeestudiantes.domain.asignatura.usecase
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import javax.inject.Inject


class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository,
) {
    private val validations = AsignaturaValidationsUseCase(repository)

    suspend operator fun invoke(asignatura: Asignatura): Result<Unit> {

        val codigoResult = validations.validateCodigo(
            value = asignatura.codigo,
            currentId = asignatura.asignaturaId
        )
        if (codigoResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(codigoResult.exceptionOrNull()?.message)
            )
        }

        val nombreResult = validations.validateNombre(
            value = asignatura.nombre,
            currentId = asignatura.asignaturaId
        )
        if (nombreResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(nombreResult.exceptionOrNull()?.message)
            )
        }

        val aulaResult = validations.validateAula(value = asignatura.aula)
        if (aulaResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(aulaResult.exceptionOrNull()?.message)
            )
        }

        val creditosResult = validations.validateCreditos(value = asignatura.creditos.toString())
        if (creditosResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(creditosResult.exceptionOrNull()?.message)
            )
        }

        return runCatching {
            repository.upsert(asignatura)
        }
    }
}