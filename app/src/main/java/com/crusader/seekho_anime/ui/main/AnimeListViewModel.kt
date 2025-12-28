package com.crusader.seekho_anime.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crusader.seekho_anime.data.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val repo: AnimeRepository
) : ViewModel() {

    val animeList = repo.animeList

    init {
        viewModelScope.launch {
            repo.refreshAnime()
        }
    }
}

