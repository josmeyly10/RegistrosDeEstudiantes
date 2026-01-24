package com.example.registrodeestudiantes.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrodeestudiantes.data.local.dao.AsignaturaDao
import com.example.registrodeestudiantes.data.local.entities.AsignaturaEntity
@Database(
    entities = [AsignaturaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AsignaturaDb : RoomDatabase() {
    abstract fun asignaturaDao(): AsignaturaDao
}