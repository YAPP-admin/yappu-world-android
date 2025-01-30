package com.yapp.core.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.local.generateSecurityPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.thdev.useful.encrypted.data.store.preferences.security.generateUsefulSecurity
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) },
        )
    }

    @EncryptedDataStore
    @Singleton
    @Provides
    fun providedEncryptedDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            produceFile = { context.preferencesDataStoreFile(ENCRYPTED_DATASTORE_NAME) },
        )
    }

    @Singleton
    @Provides
    fun provideSecurityPreference(
        @EncryptedDataStore dataStore: DataStore<Preferences>,
    ): SecurityPreferences = dataStore.generateSecurityPreferences(generateUsefulSecurity())

    private const val DATASTORE_NAME = "yapp-datastore"
    private const val ENCRYPTED_DATASTORE_NAME = "yapp-datastore-encrypted"
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class EncryptedDataStore
