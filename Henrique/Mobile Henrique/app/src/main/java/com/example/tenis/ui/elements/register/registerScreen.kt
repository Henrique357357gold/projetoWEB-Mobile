package com.example.tenis.ui.elements.register

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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tenis.ui.elements.input.InputButton
import com.example.tenis.ui.elements.input.InputText
import com.example.tenis.ui.elements.login.LoginState
import com.example.tenis.ui.theme.majorMonoDisplaytFontFamily

@Composable
fun registerScreen(
    viewModel: registerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onNavigateClick: () -> Unit
) {

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navigateToHome()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize(),
    ) {
        Box (modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            registerForm(
                loginState = loginState,
                viewModel = viewModel,
                navigateToHome = navigateToHome
            )
            Button(
                onClick = onNavigateClick,
                modifier = Modifier
                    .offset(
                        x = -100.dp,
                        y = 225.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Row {
                    Icon(
                        modifier = Modifier
                            .rotate(180f),
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "play",
                    )
                    Text("Faça Login")
                }
            }
        }
    }
}



@Composable
fun registerForm(
    loginState: LoginState,
    viewModel: registerViewModel,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Coleção de Tênis",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Registro",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
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
                viewModel.register(email, password)
                if (loginState is LoginState.Success) {
                    navigateToHome()
                }
            },
            modifier = Modifier
                .padding(10.dp),
            text = "Registrar"
        )
    }
}

@Composable
fun registerTitle(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "Finance",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            fontFamily = majorMonoDisplaytFontFamily,
        )
        Text(
            text = "Vision",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontFamily = majorMonoDisplaytFontFamily,
        )
    }
}