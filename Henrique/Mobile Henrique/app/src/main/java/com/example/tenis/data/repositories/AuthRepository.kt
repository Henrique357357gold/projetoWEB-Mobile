package com.example.tenis.data.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<Boolean>

    suspend fun register(email: String, password: String): Result<Boolean>

    fun logout()

    fun isUserLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?

}