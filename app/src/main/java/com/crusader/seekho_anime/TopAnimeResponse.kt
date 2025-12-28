package com.crusader.seekho_anime

data class TopAnimeResponse(
    val data: List<AnimeDto>
)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val title_english: String?,
    val episodes: Int?,
    val score: Double?,
    val images: Images
)

data class Images(
    val jpg: Jpg
)

data class Jpg(
    val image_url: String
)
