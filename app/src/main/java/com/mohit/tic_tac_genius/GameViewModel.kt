package com.mohit.tic_tac_genius

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GameState(
    val board: List<List<String>> = List(3) { List(3) { "" } },
    val isCellEnabled: Boolean = true,
    val statusMessage: String = "Player X's turn"
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> get() = _uiState

    private var isXTurn = true

    fun onCellClick(row: Int, col: Int) {
        val currentBoard = _uiState.value.board.map { it.toMutableList() }
        if (currentBoard[row][col].isEmpty()) {
            currentBoard[row][col] = if (isXTurn) "X" else "O"
            isXTurn = !isXTurn
            _uiState.value = _uiState.value.copy(
                board = currentBoard,
                statusMessage = if (checkWin(currentBoard)) {
                    "Player ${if (isXTurn) "O" else "X"} wins!"
                } else {
                    "Player ${if (isXTurn) "X" else "O"}'s turn"
                },
                isCellEnabled = !checkWin(currentBoard)
            )
        }
    }

    fun resetGame() {
        _uiState.value = GameState()
        isXTurn = true
    }

    private fun checkWin(board: List<List<String>>): Boolean {
        val lines = listOf(
            // Rows
            listOf(board[0][0], board[0][1], board[0][2]),
            listOf(board[1][0], board[1][1], board[1][2]),
            listOf(board[2][0], board[2][1], board[2][2]),
            // Columns
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            // Diagonals
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )
        return lines.any { it.all { cell -> cell == "X" } || it.all { cell -> cell == "O" } }
    }
}
