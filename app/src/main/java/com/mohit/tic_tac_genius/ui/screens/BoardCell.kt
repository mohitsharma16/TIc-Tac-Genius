package com.mohit.tic_tac_genius.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoardCell(
    value: String,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp) // Spacing between cells
            .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            fontSize = 32.sp,
            color = if (value == "X") Color.Blue else Color.Red
        )
    }
}
