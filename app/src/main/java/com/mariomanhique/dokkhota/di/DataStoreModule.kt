package com.mariomanhique.dokkhota.di
import com.mariomanhique.dokkhota.di.Anotations.NiaDispatchers


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.mariomanhique.dokkhota.data.repository.userDataRepository.UserDataRepository
import com.mariomanhique.dokkhota.data.repository.userDataRepository.UserDataRepositoryImpl
import com.mariomanhique.dokkhota.data.repository.userDataRepository.UserPreferencesSerializer
import com.mariomanhique.dokkhota.model.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Anotations.Dispatcher(NiaDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @Anotations.ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
//            migrations = listOf(
//                IntToStringIdsMigration,
//            ),
        ) {
            context.dataStoreFile("user_preferences.json")
        }

    @Singleton
    @Provides
    fun provideUerDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository {
        return userDataRepository
    }
}
