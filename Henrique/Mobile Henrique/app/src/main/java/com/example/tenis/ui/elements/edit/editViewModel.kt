package com.example.tenis.ui.elements.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenis.data.models.Item
import com.example.tenis.data.repositories.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class editViewModel @Inject constructor (
    private val repository: ItemsRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun loadFinanceById(id: String) {
        viewModelScope.launch {
            repository.getItem(id).onSuccess { finance ->
                Log.d("DetailsViewModel", "Fetched finance: $finance")
                itemUiState = ItemUiState(itemDetails = finance.toItemDetails())
            }.onFailure { exception ->
                // Lidar com o erro, se necessário
                Log.e("DetailsViewModel", "Error fetching finance by ID", exception)
            }
        }
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            marca.isNotBlank() && modelo.isNotBlank() && tamanho.isNotBlank() && cor.isNotBlank()
        }
    }

    suspend fun updateFinance() {
        if (validateInput()) {
            repository.updateItem(itemUiState.itemDetails.id, itemUiState.itemDetails.toItem())
        }
    }

}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: String = "",
    val marca: String = "",
    val modelo: String = "",
    val tamanho: String = "",
    val cor: String = ""
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    marca = marca,
    modelo = modelo,
    tamanho = tamanho,
    cor = cor
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    marca = marca,
    modelo = modelo,
    tamanho = tamanho,
    cor = cor
)