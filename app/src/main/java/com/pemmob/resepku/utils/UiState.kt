package com.pemmob.resepku.utils

/**
 * Sealed interface untuk merepresentasikan state data pada UI (State-Driven UI).
 * Memenuhi syarat modul poin 4:
 * 1. Loading state
 * 2. Error state
 * 3. Success state
 * 4. Empty state (ketika hasil pencarian tidak ditemukan)
 */
sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    object Empty : UiState<Nothing>
}
