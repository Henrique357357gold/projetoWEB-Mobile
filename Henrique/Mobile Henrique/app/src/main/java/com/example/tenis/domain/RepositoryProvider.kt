package com.example.tenis.domain

import android.app.Application
import com.example.tenis.data.AuthRepositoryImpl
import com.example.tenis.data.ItemsRepositoryImpl
import com.example.tenis.data.repositories.AuthRepository
import com.example.tenis.data.repositories.ItemsRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    fun provideFirebaseApp(application: Application): FirebaseApp {
        return FirebaseApp.initializeApp(application) ?: FirebaseApp.getInstance()
    }

    @Provides
    fun provideItemsRepository(firestore: FirebaseFirestore, authRepository: AuthRepositoryImpl): ItemsRepository {
        return ItemsRepositoryImpl(firestore, authRepository)
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}