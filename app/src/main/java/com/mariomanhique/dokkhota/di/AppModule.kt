package com.mariomanhique.dokkhota.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepositoryImpl
import com.mariomanhique.dokkhota.data.repository.profileRepository.ProfileRepository
import com.mariomanhique.dokkhota.data.repository.profileRepository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore
            = Firebase.firestore

    @Singleton
    @Provides
    fun provideProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository {
        return profileRepository
    }

    @Singleton
    @Provides
    fun provideExamsRepository(examsRepository: ExamsRepositoryImpl): ExamsRepository {
        return examsRepository
    }


}