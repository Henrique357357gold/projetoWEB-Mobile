package com.example.tenis.data

import android.util.Log
import com.example.tenis.data.models.Item
import com.example.tenis.data.repositories.ItemsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepositoryImpl
) : ItemsRepository {

    val currentUser = authRepository.getCurrentUser()
    val userId = currentUser?.uid

    private val collectionRef = firestore.collection("Users/${userId}/Tenis")

    override suspend fun createItem(item: Item): Result<Unit> {
        return try {
            collectionRef.add(item)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateItem(id: String, item: Item): Result<Unit> {
        return try {
            collectionRef.document(id).set(item).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteItem(id: String): Result<Unit> {
        return try {
            collectionRef.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getItem(id: String): Result<Item> {
        return try {
            val snapshot = collectionRef.document(id).get().await()
            Log.d("FinancesRepository", "getFinance SNAPSHOT: $snapshot")
            val finances = snapshot.toObject(Item::class.java)!!.copy(id = snapshot.id)
            Result.success(finances)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllItems(): Result<List<Item>> {
        return try {
            val snapshot = collectionRef.get().await()
            Log.d("FinancesRepository", "getAllFinances SNAPSHOT: ${snapshot.documents}")
            val items = snapshot.documents.map { it.toObject(Item::class.java)!!.copy(id = it.id) }
            Log.d("FinancesRepository", "getAllFinances: $items")
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}