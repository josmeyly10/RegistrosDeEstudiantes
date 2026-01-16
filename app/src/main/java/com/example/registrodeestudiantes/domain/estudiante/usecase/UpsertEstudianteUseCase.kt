package com.example.registrodeestudiantes.domain.estudiante.usecase
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import javax.inject.Inject


class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository,
) {
    private val validations = EstudianteValidationsUseCase(repository)

    suspend operator fun invoke(estudiante: Estudiante): Result<Unit> {

        val nombresResult = validations.validateNombres(
            value = estudiante.nombres,
            currentId = estudiante.estudianteId
        )
        if (nombresResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(nombresResult.exceptionOrNull()?.message)
            )
        }

        val emailResult = validations.validateEmail(value = estudiante.email)
        if (emailResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(emailResult.exceptionOrNull()?.message)
            )
        }

        val edadResult = validations.validateEdad(value = estudiante.edad.toString())
        if (edadResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(edadResult.exceptionOrNull()?.message)
            )
        }

        return runCatching {
            repository.upsert(estudiante)
        }
    }
}
