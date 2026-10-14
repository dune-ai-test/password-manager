package com.aegis.manager.di

import android.content.Context
import androidx.room.Room
import com.aegis.manager.data.crypto.CryptoBox
import com.aegis.manager.data.crypto.PlaintextCryptoBox
import com.aegis.manager.data.db.AegisDatabase
import com.aegis.manager.data.db.VaultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AegisDatabase =
        Room.databaseBuilder(context, AegisDatabase::class.java, AegisDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideVaultDao(database: AegisDatabase): VaultDao = database.vaultDao()

    /**
     * Demo build: identity transform. Swap this binding for an
     * AndroidKeyStore-backed box to turn on real encryption - no other call
     * site changes.
     */
    @Provides
    @Singleton
    fun provideCryptoBox(): CryptoBox = PlaintextCryptoBox()
}