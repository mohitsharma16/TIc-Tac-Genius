package com.mohit.tic_tac_genius

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GameState(
    val board: List<List<String>> = List(3) { List(3) { "" } },
    val isCellEnabled: Boolean = true,
    val statusMessage: String = "Player X's turn",
    val winner: String? = null, // Track the winner
    val playerXName: String = "", // Player X's name
    val playerOName: String = "", // Player O's name
    val isGameStarted: Boolean = false // Track if the game has started
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> get() = _uiState

    private var isXTurn = true

    // Function to update Player X's name
    fun updatePlayerXName(name: String) {
        _uiState.value = _uiState.value.copy(playerXName = name)
    }

    // Function to update Player O's name
    fun updatePlayerOName(name: String) {
        _uiState.value = _uiState.value.copy(playerOName = name)
    }

    // Function to start the game
    fun startGame() {
        _uiState.value = _uiState.value.copy(
            isGameStarted = true,
            statusMessage = "${_uiState.value.playerXName.ifEmpty { "Player X" }}'s turn"
        )
    }

    fun onCellClick(row: Int, col: Int) {
        val currentBoard = _uiState.value.board.map { it.toMutableList() }
        if (currentBoard[row][col].isEmpty() && _uiState.value.winner == null) {
            currentBoard[row][col] = if (isXTurn) "X" else "O"
            isXTurn = !isXTurn
            val winner = checkWin(currentBoard)
            _uiState.value = _uiState.value.copy(
                board = currentBoard,
                statusMessage = if (winner != null) {
                    "Congratulations, ${if (winner == "X") _uiState.value.playerXName.ifEmpty { "Player X" } else _uiState.value.playerOName.ifEmpty { "Player O" }} wins!"
                } else {
                    "${if (isXTurn) _uiState.value.playerXName.ifEmpty { "Player X" } else _uiState.value.playerOName.ifEmpty { "Player O" }}'s turn"
                },
                isCellEnabled = winner == null,
                winner = winner
            )
        }
    }

    fun resetGame() {
        _uiState.value = _uiState.value.copy(
            board = List(3) { List(3) { "" } },
            isCellEnabled = true,
            statusMessage = "${_uiState.value.playerXName.ifEmpty { "Player X" }}'s turn",
            winner = null
        )
        isXTurn = true
    }

    private fun checkWin(board: List<List<String>>): String? {
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
        return when {
            lines.any { it.all { cell -> cell == "X" } } -> "X"
            lines.any { it.all { cell -> cell == "O" } } -> "O"
            else -> null
        }
    }
}
