package com.pemmob.resepku.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.resepku.data.model.MealDto
import com.pemmob.resepku.data.repository.MealRepository
import com.pemmob.resepku.data.repository.MealRepositoryImpl
import com.pemmob.resepku.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola state dan data detail resep pada Detail Screen.
 * Sesuai arsitektur MVVM & Poin 4 (State-driven UI, Loading, Error).
 */
class DetailViewModel(
    private val repository: MealRepository = MealRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MealDto>>(UiState.Loading)
    val uiState: StateFlow<UiState<MealDto>> = _uiState.asStateFlow()

    private var currentMealId: String? = null

    /**
     * Memuat detail resep berdasarkan meal ID.
     */
    fun loadRecipeDetail(id: String) {
        currentMealId = id
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getRecipeDetail(id)
            result.fold(
                onSuccess = { meal ->
                    if (meal != null) {
                        _uiState.value = UiState.Success(meal)
                    } else {
                        _uiState.value = UiState.Error("Resep tidak ditemukan.")
                    }
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(
                        error.localizedMessage ?: "Gagal memuat detail resep. Periksa koneksi internet Anda."
                    )
                }
            )
        }
    }

    /**
     * Fungsi coba lagi saat terjadi error koneksi.
     */
    fun retry() {
        currentMealId?.let { loadRecipeDetail(it) }
    }
}
