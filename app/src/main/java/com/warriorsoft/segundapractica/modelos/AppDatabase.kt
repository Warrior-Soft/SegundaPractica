package com.warriorsoft.segundapractica.modelos
import androidx.room.*
import androidx.room.migration.Migration

@Database(entities = arrayOf(User::class,Meta::class,Progress::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun metaDao(): MetaDao
    abstract fun progressDao(): ProgressDao



}