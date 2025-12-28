package com.crusader.seekho_anime

data class AnimeDetailResponse(
    val data: AnimeDetailDto
)

data class AnimeDetailDto(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val images: Images,
    val trailer: Trailer?
)


data class Genre(val name: String)

data class Trailer(
    val youtube_id: String?
)
