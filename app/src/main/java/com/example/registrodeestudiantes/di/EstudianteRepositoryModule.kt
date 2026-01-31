package com.example.registrodeestudiantes.di
import com.example.registrodeestudiantes.data.repository.AsignaturaRepositoryImpl
import com.example.registrodeestudiantes.data.repository.EstudianteRepositoryImpl
import com.example.registrodeestudiantes.data.repository.TipoPenalidadRepositoryImpl
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class EstudianteRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        estudianteRepositoryImpl: EstudianteRepositoryImpl
    ): EstudianteRepository

    @Binds
    @Singleton
    abstract fun bindAsignaturaRepository(
        asignaturaRepositoryImpl: AsignaturaRepositoryImpl
    ): AsignaturaRepository

    @Binds
    @Singleton
    abstract fun bindTipoPenalidadRepository(
        tipoPenalidadRepositoryImpl: TipoPenalidadRepositoryImpl
    ): TipoPenalidadRepository


}

