package com.example.tenis.ui.elements.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tenis.ui.theme.majorMonoDisplaytFontFamily

@Composable
fun InputButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        shape = RoundedCornerShape(15)
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun InvertedInputButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        shape = RoundedCornerShape(15)
    ) {
        Text(
            text = text,
            fontFamily = majorMonoDisplaytFontFamily
        )
    }
}