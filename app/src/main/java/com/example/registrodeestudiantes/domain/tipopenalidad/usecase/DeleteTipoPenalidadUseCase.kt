package com.example.registrodeestudiantes.domain.tipopenalidad.usecase
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject


class DeleteTipoPenalidadUseCase @Inject constructor(
        private val repository: TipoPenalidadRepository
    ) {
        suspend operator fun invoke(id: Int) = repository.delete(id)
    }
