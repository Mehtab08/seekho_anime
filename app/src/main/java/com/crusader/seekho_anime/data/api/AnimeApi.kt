package com.crusader.seekho_anime.data.api

import com.crusader.seekho_anime.AnimeDetailResponse
import com.crusader.seekho_anime.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetail(
        @Path("id") id: Int
    ): AnimeDetailResponse
}



