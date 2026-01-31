package com.example.registrodeestudiantes.data.local.dao
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.registrodeestudiantes.data.local.entities.TipoPenalidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {

    @Upsert
    suspend fun upsert(tipoPenalidad: TipoPenalidadEntity)

    @Query("DELETE FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM TiposPenalidades WHERE LOWER(nombre) = LOWER(:nombre) AND tipoId != :excludeId)")
    suspend fun existsByNombre(nombre: String, excludeId: Int): Boolean

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    fun observeById(id: Int): Flow<TipoPenalidadEntity?>

    @Query("SELECT * FROM TiposPenalidades ORDER BY nombre ASC")
    fun observeAll(): Flow<List<TipoPenalidadEntity>>
}