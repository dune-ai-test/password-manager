package com.aegis.manager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VaultItemEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AegisDatabase : RoomDatabase() {
    abstract fun vaultDao(): VaultDao

    companion object {
        const val NAME = "aegis-vault.db"
    }
}