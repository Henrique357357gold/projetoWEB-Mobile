package com.example.tenis.ui.elements.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tenis.data.models.Item
import com.example.tenis.data.repositories.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: ItemsRepository
): ViewModel(){

    private val _financesState = MutableStateFlow<List<Item>>(emptyList())
    val financesState: StateFlow<List<Item>> = _financesState.asStateFlow()

    suspend fun getAllFinances(){
        try {
            // Log de início da função
            Log.d("HomeViewModel", "Fetching finances...")

            // Chama o repositório para buscar os dados
            val result = repository.getAllItems()

            // Verifica se a chamada foi bem-sucedida
            result.onSuccess { finances ->
                _financesState.value = finances
                Log.d("HomeViewModel", "Successfully fetched finances: ${finances.size} items")
            }.onFailure { exception ->
                // Log de erro em caso de falha
                Log.e("HomeViewModel", "Error fetching finances: ${exception.message}")
            }

        } catch (e: Exception) {
            // Trata qualquer erro inesperado
            Log.e("HomeViewModel", "Unexpected error: ${e.message}")
        }
    }
}
