package com.example.registrodeestudiantes.di
import com.example.registrodeestudiantes.data.repository.EstudianteRepositoryImpl
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
}
