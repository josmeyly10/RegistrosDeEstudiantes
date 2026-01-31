package com.example.registrodeestudiantes.domain.tipopenalidad.usecase
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(id: Int): Flow<TipoPenalidad?> {
        return repository.observeById(id)
    }
}