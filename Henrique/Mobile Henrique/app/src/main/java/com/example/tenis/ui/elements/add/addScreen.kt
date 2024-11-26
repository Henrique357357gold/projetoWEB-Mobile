package com.example.tenis.ui.elements.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tenis.ui.elements.input.InputButton
import com.example.tenis.ui.elements.input.InputText
import com.example.tenis.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddScreen(
    viewModel: addViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .padding(top = 38.dp)
            .fillMaxSize(),
    ) { innerPadding ->
        IconButton(
            onClick = navigateBack,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 10.dp),
            textAlign = TextAlign.Center,
            text = "Adicionar Tênis",
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )

        addForm(
            financeState = viewModel.itemUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.addFinance()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            onValueChange = viewModel::updateUiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addForm(
    financeState: ItemUiState,
    onSaveClick: () -> Unit,
    onValueChange: (ItemDetails) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedOption by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Marca",
            value = financeState.itemDetails.marca,
            onValueChange = { onValueChange(financeState.itemDetails.copy(marca = it)) }
        )
        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Modelo",
            value = financeState.itemDetails.modelo,
            onValueChange = {onValueChange(financeState.itemDetails.copy(modelo = it))}
        )

        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Tamanho",
            value = financeState.itemDetails.tamanho,
            onValueChange = {onValueChange(financeState.itemDetails.copy(tamanho = it))}
        )

        InputText(
            modifier = Modifier.fillMaxWidth(),
            title = "Cor",
            value = financeState.itemDetails.cor,
            onValueChange = {onValueChange(financeState.itemDetails.copy(cor = it))}
        )

        InputButton(
            onClick = onSaveClick,
            text = "Salvar"
        )

    }
}