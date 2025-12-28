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
    val trailer: Trailer?,
    val genres: List<Genre>
)

data class Trailer(
    val youtube_id: String?
)

data class Genre(
    val name: String
)
