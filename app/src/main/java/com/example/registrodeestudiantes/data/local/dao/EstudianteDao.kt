package com.example.registrodeestudiantes.data.local.dao
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.registrodeestudiantes.data.local.entities.EstudianteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EstudianteDao {

    @Upsert
    suspend fun upsert(estudiante: EstudianteEntity)

    @Query("DELETE FROM Estudiantes WHERE estudianteId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM Estudiantes WHERE estudianteId = :id")
    suspend fun getById(id: Int): EstudianteEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM Estudiantes WHERE LOWER(nombres) = LOWER(:nombres) AND estudianteId != :excludeId)")
    suspend fun existsByNombres(nombres: String, excludeId: Int): Boolean

    @Query("SELECT * FROM Estudiantes WHERE estudianteId = :id")
    fun observeById(id: Int): Flow<EstudianteEntity?>

    @Query("SELECT * FROM Estudiantes ORDER BY nombres ASC")
    fun observeAll(): Flow<List<EstudianteEntity>>
}