package com.rbraithwaite.yourmovielog.data.network.models

data class Genre(
    val id: Long,
    val name: String
)

data class Genres(
    val genres: List<Genre>
)
