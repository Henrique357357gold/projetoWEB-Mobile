package com.example.tenis.ui.elements.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tenis.data.models.Item
import com.example.tenis.data.repositories.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class addViewModel @Inject constructor (
    private val repository: ItemsRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            marca.isNotBlank() && modelo.isNotBlank() && tamanho.isNotBlank() && cor.isNotBlank()
        }
    }

    suspend fun addFinance() {
        if (validateInput()) {
            repository.createItem(itemUiState.itemDetails.toItem())
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