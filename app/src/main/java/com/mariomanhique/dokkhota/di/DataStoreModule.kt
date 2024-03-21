//package com.mariomanhique.diaryapp.di
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.DataStoreFactory
//import androidx.datastore.dataStoreFile
//import com.mariomanhique.diaryapp.UserPreferences
//import com.mariomanhique.diaryapp.data.UserDataRepository
//import com.mariomanhique.diaryapp.data.UserDataRepositoryImpl
//import com.mariomanhique.diaryapp.data.UserPreferencesSerializer
//import com.mariomanhique.diaryapp.util.ApplicationScope
//import com.mariomanhique.diaryapp.util.Dispatcher
//import com.mariomanhique.diaryapp.util.NiaDispatchers
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//import javax.inject.Singleton
//
//
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreModule {
//    @Provides
//    @Singleton
//    fun providesUserPreferencesDataStore(
//        @ApplicationContext context: Context,
//        @Dispatcher(NiaDispatchers.IO) ioDispatcher: CoroutineDispatcher,
//        @ApplicationScope scope: CoroutineScope,
//        userPreferencesSerializer: UserPreferencesSerializer,
//    ): DataStore<UserPreferences> =
//        DataStoreFactory.create(
//            serializer = userPreferencesSerializer,
//            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
////            migrations = listOf(
////                IntToStringIdsMigration,
////            ),
//        ) {
//            context.dataStoreFile("user_preferences.pb")
//        }
//
//    @Singleton
//    @Provides
//    fun provideUerDataRepository(
//        userDataRepository: UserDataRepositoryImpl
//    ): UserDataRepository {
//        return userDataRepository
//    }
//}
