package com.example.tenis.ui.elements.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tenis.ui.elements.input.InputButton
import com.example.tenis.ui.theme.Black

@Composable
fun AccountScreen(
    viewModel: accountViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {

    IconButton(
        onClick = {
            onNavigateBack()
        },
        modifier = Modifier
            .offset(
                y = 38.dp
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = ""
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = viewModel.getCurrentUser()?.email ?: "",
            color = Black,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(30.dp))
        InputButton(
            text = "Logout",
            onClick = {
                viewModel.logout()
                onLogout()
            }
        )
    }

}