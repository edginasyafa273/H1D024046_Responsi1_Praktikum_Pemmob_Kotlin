package com.pemmob.resepku.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pemmob.resepku.ui.components.EmptyView
import com.pemmob.resepku.ui.components.ErrorView
import com.pemmob.resepku.ui.components.LoadingView
import com.pemmob.resepku.ui.components.RecipeCard
import com.pemmob.resepku.ui.components.RecipeSearchBar
import com.pemmob.resepku.utils.UiState

/**
 * HomeScreen dengan desain Modern Minimalis bernuansa Merah Tua.
 * 100% Memenuhi spesifikasi modul:
 * 1. Judul Aplikasi
 * 2. Search bar (pencarian berdasarkan nama makanan)
 * 3. Daftar resep (LazyVerticalGrid)
 * 4. Gambar, Nama, dan Kategori makanan
 * 5. State-driven UI (Loading, Error, Empty, Success)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRecipeClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ResepKu",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = (-0.5).sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. Search Bar Modern Minimalis (Pencarian Nama Makanan Sesuai Modul)
            RecipeSearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onSearch = { viewModel.onSearchTriggered() }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 2. State-Driven UI Handling
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (val state = uiState) {
                    is UiState.Loading -> {
                        LoadingView()
                    }

                    is UiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = { viewModel.retry() }
                        )
                    }

                    is UiState.Empty -> {
                        EmptyView(
                            message = "Tidak ada resep yang cocok dengan '$searchQuery'. Silakan coba cari dengan nama masakan lain (misal: chicken, pie, cake)."
                        )
                    }

                    is UiState.Success -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Label info jumlah resep
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (searchQuery.isBlank()) "Daftar Resep" else "Hasil Pencarian",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${state.data.size} Resep",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Komponen Lazy Layout (LazyVerticalGrid)
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = state.data,
                                    key = { it.idMeal ?: it.hashCode().toString() }
                                ) { meal ->
                                    RecipeCard(
                                        meal = meal,
                                        onClick = onRecipeClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
