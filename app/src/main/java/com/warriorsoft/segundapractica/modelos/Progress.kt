package com.warriorsoft.segundapractica.modelos
import androidx.room.*

@Entity
data class Progress(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "progreso") val progreso: Double,
        @ColumnInfo(name = "fecha") val fecha: String,
        @ColumnInfo(name = "metaid") val metaid: Int?,

)
@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    fun getAll(): List<Progress>

    @Query("SELECT * FROM progress WHERE metaid IN (:metaIds)")
    fun loadAllByIds(metaIds: Int): List<Progress>
/*
    @Query("SELECT * FROM user WHERE email LIKE :email AND " +
            "pass LIKE :pass LIMIT 1")
    fun findByName(email: String, pass: String): User*/

    @Insert
    fun insertAll(vararg progresos: Progress)

    @Delete
    fun delete(progre: Progress)
}