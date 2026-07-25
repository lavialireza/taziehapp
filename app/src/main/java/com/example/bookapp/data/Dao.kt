package com.example.bookapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FieldDao {
    @Query("SELECT * FROM fields ORDER BY id")
    suspend fun getAll(): List<FieldEntity>

    @Insert
    suspend fun insert(field: FieldEntity): Long
}

@Dao
interface TaziehDao {
    @Query("SELECT * FROM taziehs WHERE fieldId = :fieldId ORDER BY id")
    suspend fun getByField(fieldId: Long): List<TaziehEntity>

    @Insert
    suspend fun insert(tazieh: TaziehEntity): Long
}

@Dao
interface RoleDao {
    @Query("SELECT * FROM roles WHERE taziehId = :taziehId ORDER BY id")
    suspend fun getByTazieh(taziehId: Long): List<RoleEntity>

    @Insert
    suspend fun insert(role: RoleEntity): Long
}

@Dao
interface SectionDao {
    @Query("SELECT * FROM sections WHERE roleId = :roleId ORDER BY orderIndex")
    suspend fun getByRole(roleId: Long): List<SectionEntity>

    @Query("SELECT * FROM sections WHERE id = :sectionId")
    suspend fun getById(sectionId: Long): SectionEntity

    @Insert
    suspend fun insert(section: SectionEntity): Long
}
