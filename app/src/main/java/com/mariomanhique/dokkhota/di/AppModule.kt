package com.mariomanhique.dokkhota.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mariomanhique.dokkhota.data.repository.analyticsRepository.AnalyticsRepository
import com.mariomanhique.dokkhota.data.repository.analyticsRepository.AnalyticsRepositoryImpl
import com.mariomanhique.dokkhota.data.repository.authRepository.AuthRepository
import com.mariomanhique.dokkhota.data.repository.authRepository.AuthRepositoryImpl
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
    fun provideProfileRepository(
        profileRepository: ProfileRepositoryImpl
    ): ProfileRepository = profileRepository

    @Singleton
    @Provides
    fun provideExamsRepository(
        examsRepository: ExamsRepositoryImpl
    ): ExamsRepository = examsRepository

    @Singleton
    @Provides
    fun provideAuthRepository(
        authRepository: AuthRepositoryImpl
    ):AuthRepository = authRepository

    @Singleton
    @Provides
    fun provideAnalyticsRepository(
        analyticsRepositoryImpl: AnalyticsRepositoryImpl
    ):AnalyticsRepository = analyticsRepositoryImpl


}