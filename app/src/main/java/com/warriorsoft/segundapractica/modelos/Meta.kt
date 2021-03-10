package com.warriorsoft.segundapractica.modelos
import androidx.room.*

@Entity
data class Meta(
    @PrimaryKey(autoGenerate = true) val uid:Int,
    @ColumnInfo(name = "peso_actual") val peso_Actual: Double?,
    @ColumnInfo(name = "peso_objetivo") val peso_Objetivo: Double?,
    @ColumnInfo(name = "peso_restante") val peso_Restante: Double?,
    @ColumnInfo(name = "persona_id") val persona_id: Int?

)

@Dao
interface MetaDao {
    @Query("SELECT * FROM meta")
    fun getAll(): List<Meta>

    @Query("SELECT * FROM meta WHERE uid IN (:metaIds)")
    fun loadAllByIds(metaIds: IntArray): List<Meta>

    @Query("SELECT * FROM meta WHERE persona_id IN (:persona_id) LIMIT 1 ")
    fun findByPersona(persona_id: Int?): Meta

    @Query("SELECT * FROM meta WHERE uid IN (:metaid) LIMIT 1 ")
    fun findByMeta(metaid: Int?): Meta

    @Insert
    fun insertAll(vararg metas: Meta)

    @Update
    fun updateMeta(meta:Meta)

    @Delete
    fun delete(user: Meta)
}
