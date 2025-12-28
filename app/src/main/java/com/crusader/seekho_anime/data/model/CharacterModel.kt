package com.crusader.seekho_anime.data.model

data class CharactersResponse(
    val data: List<CharacterData>
)

data class CharacterData(
    val character: Character
)

data class Character(
    val name: String
)
