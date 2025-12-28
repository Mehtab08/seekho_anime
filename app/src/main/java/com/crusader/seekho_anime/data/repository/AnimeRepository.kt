package com.crusader.seekho_anime.data.repository

import android.util.Log
import com.crusader.seekho_anime.AnimeDetailResponse
import com.crusader.seekho_anime.data.api.AnimeApi
import com.crusader.seekho_anime.data.db.AnimeDao
import com.crusader.seekho_anime.data.db.AnimeEntity

class AnimeRepository(
    private val api: AnimeApi,
    private val dao: AnimeDao
) {

    val animeList = dao.getAllAnime()

    suspend fun refreshAnime() {
        val response = api.getTopAnime()

        val entities = response.data.map {
            AnimeEntity(
                id = it.mal_id,
                title = it.title_english ?: it.title,
                episodes = it.episodes ?: 0,
                score = it.score ?: 0.0,
                imageUrl = it.images.jpg.image_url
            )
        }

        dao.clearAll()
        dao.insertAll(entities)
    }

    suspend fun getAnimeDetail(id: Int): AnimeDetailResponse {
        return api.getAnimeDetail(id)
    }
}
