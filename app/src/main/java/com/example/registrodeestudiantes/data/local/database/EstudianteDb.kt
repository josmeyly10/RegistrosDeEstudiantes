package com.example.registrodeestudiantes.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrodeestudiantes.data.local.dao.EstudianteDao
import com.example.registrodeestudiantes.data.local.entities.EstudianteEntity

@Database(
    entities = [
        EstudianteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EstudianteDb : RoomDatabase() {

    abstract fun estudianteDao(): EstudianteDao
}
