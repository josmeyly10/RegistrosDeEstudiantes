package com.example.registrodeestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.registrodeestudiantes.data.local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {

    @Upsert
    suspend fun upsert(asignatura: AsignaturaEntity)

    @Query("DELETE FROM asignaturas WHERE asignaturaId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun getById(id: Int): AsignaturaEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM asignaturas WHERE LOWER(nombre) = LOWER(:nombre) AND asignaturaId != :excludeId)")
    suspend fun existsByNombre(nombre: String, excludeId: Int): Boolean
    @Query("SELECT EXISTS(SELECT 1 FROM asignaturas WHERE LOWER(codigo) = LOWER(:codigo) AND asignaturaId != :excludeId)")
    suspend fun existsByCodigo(codigo: String, excludeId: Int): Boolean
    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    fun observeById(id: Int): Flow<AsignaturaEntity?>

    @Query("SELECT * FROM asignaturas ORDER BY nombre ASC")
    fun observeAll(): Flow<List<AsignaturaEntity>>
}