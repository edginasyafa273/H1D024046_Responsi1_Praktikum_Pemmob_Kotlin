package com.pemmob.resepku.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.resepku.data.model.MealDto
import com.pemmob.resepku.data.repository.MealRepository
import com.pemmob.resepku.data.repository.MealRepositoryImpl
import com.pemmob.resepku.utils.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola state dan logika bisnis pada Home Screen.
 * Sesuai arsitektur MVVM & Poin 4 (State-driven UI, Search, Loading, Error).
 */
class HomeViewModel(
    private val repository: MealRepository = MealRepositoryImpl()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<MealDto>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MealDto>>> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Memuat resep awal saat aplikasi pertama kali dibuka
        fetchRecipes("")
    }

    /**
     * Memperbarui teks pencarian dari user input.
     * Menggunakan debounce 500ms agar tidak membebani API di setiap ketikan huruf.
     */
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            fetchRecipes(newQuery)
        }
    }

    /**
     * Menjalankan pencarian resep langsung (misal saat menekan tombol search/enter pada keyboard).
     */
    fun onSearchTriggered() {
        searchJob?.cancel()
        fetchRecipes(_searchQuery.value)
    }

    /**
     * Mengambil daftar resep dari repository dan memperbarui UI state.
     */
    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.searchRecipes(query)
            result.fold(
                onSuccess = { meals ->
                    if (meals.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(meals)
                    }
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(
                        error.localizedMessage ?: "Terjadi kesalahan saat memuat resep. Periksa koneksi internet Anda."
                    )
                }
            )
        }
    }

    /**
     * Fungsi coba lagi saat terjadi error koneksi.
     */
    fun retry() {
        fetchRecipes(_searchQuery.value)
    }
}
