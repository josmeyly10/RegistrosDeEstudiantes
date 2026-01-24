package com.example.registrodeestudiantes.di
import android.content.Context
import androidx.room.Room
import com.example.registrodeestudiantes.data.local.dao.AsignaturaDao
import com.example.registrodeestudiantes.data.local.database.EstudianteDb
import com.example.registrodeestudiantes.data.local.dao.EstudianteDao
import com.example.registrodeestudiantes.data.local.database.AsignaturaDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideEstudianteDb(
        @ApplicationContext appContext: Context
    ): EstudianteDb = Room.databaseBuilder(
        appContext,
        EstudianteDb::class.java,
        "Estudiante.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideEstudianteDao(database: EstudianteDb): EstudianteDao {
        return database.estudianteDao()
    }


    @Provides
    @Singleton
    fun provideAsignaturaDb(
        @ApplicationContext appContext: Context
    ): AsignaturaDb = Room.databaseBuilder(
        appContext,
        AsignaturaDb::class.java,
        "Asignatura.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideAsignaturaDao(database: AsignaturaDb): AsignaturaDao {
        return database.asignaturaDao()
    }


}
