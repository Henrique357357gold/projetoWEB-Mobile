package com.example.tenis.ui.elements.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tenis.ui.elements.input.InputButton
import com.example.tenis.ui.elements.input.InputText

@Composable
fun loginScreen(
    viewModel: loginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateClick: () -> Unit,
    navigateToHome: () -> Unit,
) {

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navigateToHome()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
    ) {
        Box (modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            loginForm(
                loginState = loginState,
                viewModel = viewModel,
                navigateToHome = {
                    navigateToHome()
                }
            )
            Button(
                onClick = onNavigateClick,
                modifier = Modifier
                    .offset(
                        x = 100.dp,
                        y = 225.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Row {
                    Text("Registre-se")
                    Icon(

                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "play",
                        tint = Color.Black
                    )
                }

            }
        }
    }
}



@Composable
fun loginForm(
    loginState: LoginState,
    viewModel: loginViewModel,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Coleção de Tênis",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row {
            Text(
                text = "Login",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        InputText(
            title = "E-mail",
            value = email,
            onValueChange = {email = it}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        InputText(
            title = "Senha",
            value = password,
            onValueChange = {password = it}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        InputButton(
            onClick = {
                viewModel.login(email, password)
                if (loginState is LoginState.Success) {
                    navigateToHome()
                }
            },
            modifier = Modifier
                .padding(10.dp),
            text = "Entrar"
        )
    }
}

@Composable
fun loginTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Aplicativo de Agendamento",
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
    )
}
