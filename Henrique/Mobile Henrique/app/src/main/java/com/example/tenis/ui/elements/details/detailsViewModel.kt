package com.example.tenis.ui.elements.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenis.data.models.Item
import com.example.tenis.data.repositories.ItemsRepository
import com.example.tenis.ui.elements.add.ItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class detailsViewModel @Inject constructor(
    private val repository: ItemsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailsUiState())
    val uiState: StateFlow<ItemDetailsUiState> = _uiState

    fun loadFinanceById(id: String) {
        viewModelScope.launch {
            repository.getItem(id).onSuccess { finance ->
                Log.d("DetailsViewModel", "Fetched finance: $finance")
                _uiState.value = ItemDetailsUiState(itemDetails = finance.toItemDetails())
            }.onFailure { exception ->
                // Lidar com o erro, se necessário
                Log.e("DetailsViewModel", "Error fetching finance by ID", exception)
            }
        }
    }

    suspend fun deleteItem() {
        Log.d("DetailsViewModel", "Deleting item with ID: ${uiState.value.itemDetails.id}")
        repository.deleteItem(uiState.value.itemDetails.id)
        Log.d("DetailsViewModel", "Item deleted successfully")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ItemDetailsUiState(
    val itemDetails: ItemDetails = ItemDetails()
)


fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    marca = marca,
    modelo = modelo,
    tamanho = tamanho,
    cor = cor
)