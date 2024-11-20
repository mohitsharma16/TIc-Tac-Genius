package com.mohit.tic_tac_genius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohit.tic_tac_genius.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mohit.tic_tac_genius.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF87CEEB)) // Sky Blue Background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Tic Tac Genius",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name Input Fields
        if (!state.value.isGameStarted) {
            TextField(
                value = state.value.playerXName,
                onValueChange = { viewModel.updatePlayerXName(it) },
                label = { Text("Player X Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.value.playerOName,
                onValueChange = { viewModel.updatePlayerOName(it) },
                label = { Text("Player O Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.startGame() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start Game")
            }
        } else {
            // Game Board with Rounded Corners
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Draw the grid lines
                TicTacToeGrid()

                // Game Board
                Column {
                    for (i in 0 until 3) {
                        Row {
                            for (j in 0 until 3) {
                                BoardCell(
                                    value = state.value.board[i][j],
                                    onClick = { viewModel.onCellClick(i, j) },
                                    isEnabled = state.value.isCellEnabled && state.value.board[i][j].isEmpty()
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status Message
        Text(
            text = state.value.statusMessage,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Button
        Button(
            onClick = { viewModel.resetGame() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset Game")
        }
    }

    // Winner Popup Dialog
    if (state.value.winner != null) {
        AlertDialog(
            onDismissRequest = { /* Prevent dismissal */ },
            title = { Text(text = "Congratulations!") },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Lottie party popper animation
                    val compositionResult = rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(R.raw.party_popper)
                    )
                    val composition = compositionResult.value

                    if (composition != null) {
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(250.dp)
                        )
                    }
                    val winnerName = if (state.value.winner == "X") {
                        state.value.playerXName
                    } else {
                        state.value.playerOName
                    }
                    Text(
                        text = "$winnerName wins!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.resetGame() }) {
                    Text("OK")
                }
            }
        )

        // Automatically reset the game after 5 seconds
        LaunchedEffect(Unit) {
            delay(5000)
            viewModel.resetGame()
        }
    }
}
