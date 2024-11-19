package com.mohit.tic_tac_genius.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun TicTacToeGrid() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val lineThickness = 6f // Define the thickness of the grid lines

        // Draw vertical lines
        drawLine(
            color = Color.Black,
            start = androidx.compose.ui.geometry.Offset(x = width / 3, y = 0f),
            end = androidx.compose.ui.geometry.Offset(x = width / 3, y = height),
            strokeWidth = lineThickness,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = androidx.compose.ui.geometry.Offset(x = 2 * width / 3, y = 0f),
            end = androidx.compose.ui.geometry.Offset(x = 2 * width / 3, y = height),
            strokeWidth = lineThickness,
            cap = StrokeCap.Round
        )

        // Draw horizontal lines
        drawLine(
            color = Color.Black,
            start = androidx.compose.ui.geometry.Offset(x = 0f, y = height / 3),
            end = androidx.compose.ui.geometry.Offset(x = width, y = height / 3),
            strokeWidth = lineThickness,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = androidx.compose.ui.geometry.Offset(x = 0f, y = 2 * height / 3),
            end = androidx.compose.ui.geometry.Offset(x = width, y = 2 * height / 3),
            strokeWidth = lineThickness,
            cap = StrokeCap.Round
        )
    }
}
