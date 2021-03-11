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

    @Query("SELECT * FROM progress WHERE metaid IN (:metaids) Order by(uid) desc limit 1")
    fun findLastProgress(metaids: Int): Progress

    @Query("SELECT * FROM progress WHERE metaid IN (:metaids) Order by(uid) desc limit 4")
    fun findMonthProgress(metaids: Int): List<Progress>

    @Insert
    fun insertAll(vararg progresos: Progress)

    @Delete
    fun delete(progre: Progress)
}