package com.example.registrodeestudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrodeestudiantes.data.local.dao.TipoPenalidadDao
import com.example.registrodeestudiantes.data.local.entities.TipoPenalidadEntity

@Database(
    entities = [
        TipoPenalidadEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TipoPenalidadDb : RoomDatabase() {

    abstract fun TipoPenalidadDao(): TipoPenalidadDao
}