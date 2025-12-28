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

    private val _detail = MutableStateFlow<AnimeDetailDto?>(null)
    val detail: StateFlow<AnimeDetailDto?> = _detail

    private val _cast = MutableStateFlow<List<String>>(emptyList())
    val cast: StateFlow<List<String>> = _cast

    fun load(id: Int) {
        viewModelScope.launch {
            val detailResponse = repo.getAnimeDetail(id)
            _detail.value = detailResponse.data

            val characters = repo.getCharacters(id)
            _cast.value = characters.data
                .take(5)
                .map { it.character.name }
        }
    }
}

