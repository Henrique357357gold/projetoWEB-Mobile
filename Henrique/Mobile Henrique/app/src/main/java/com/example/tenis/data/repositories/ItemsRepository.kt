package com.example.tenis.data.repositories

import com.example.tenis.data.models.Item

interface ItemsRepository {

    suspend fun createItem(item: Item) : Result<Unit>

    suspend fun updateItem(id: String, item: Item) : Result<Unit>

    suspend fun deleteItem(id: String) : Result<Unit>

    suspend fun getItem(id: String) : Result<Item>

    suspend fun getAllItems(): Result<List<Item>>

}