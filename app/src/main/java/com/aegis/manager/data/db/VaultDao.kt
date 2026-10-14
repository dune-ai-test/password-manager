package com.aegis.manager.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {

    @Query("SELECT * FROM vault_items ORDER BY title COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<VaultItemEntity>>

    @Query("SELECT * FROM vault_items WHERE id = :id LIMIT 1")
    fun observeById(id: Long): Flow<VaultItemEntity?>

    @Query("SELECT * FROM vault_items WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): VaultItemEntity?

    @Query("SELECT * FROM vault_items ORDER BY title COLLATE NOCASE ASC")
    suspend fun findAllOnce(): List<VaultItemEntity>

    @Query("SELECT COUNT(*) FROM vault_items")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: VaultItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<VaultItemEntity>)

    @Update
    suspend fun update(item: VaultItemEntity)

    @Delete
    suspend fun delete(item: VaultItemEntity)
}