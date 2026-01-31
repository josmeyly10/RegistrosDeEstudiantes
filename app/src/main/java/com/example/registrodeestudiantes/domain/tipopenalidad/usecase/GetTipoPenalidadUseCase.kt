package com.example.registrodeestudiantes.domain.tipopenalidad.usecase
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject


class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? {
        return repository.getById(id)
    }
}