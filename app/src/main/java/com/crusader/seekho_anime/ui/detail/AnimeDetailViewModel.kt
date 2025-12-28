package com.crusader.seekho_anime.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crusader.seekho_anime.AnimeDetailDto
import com.crusader.seekho_anime.AnimeDetailResponse
import com.crusader.seekho_anime.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repo: AnimeRepository
) : ViewModel() {

    private val _detail = MutableStateFlow<AnimeDetailResponse?>(null)
    val detail: StateFlow<AnimeDetailResponse?> = _detail

    fun load(id: Int) {
        viewModelScope.launch {
            _detail.value = repo.getAnimeDetail(id)
        }
    }
}
