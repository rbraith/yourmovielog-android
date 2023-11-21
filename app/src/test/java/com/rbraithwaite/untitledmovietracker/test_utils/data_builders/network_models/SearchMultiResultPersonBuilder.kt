package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultPerson
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.Builder

fun aSearchMultiResultPerson(
    buildBlock: SearchMultiResultPersonBuilder.() -> Unit = {}
): SearchMultiResultPersonBuilder {
    return SearchMultiResultPersonBuilder().apply(buildBlock)
}

class SearchMultiResultPersonBuilder: Builder<SearchMultiResultPerson> {
    private var data = SearchMultiResultPerson(
        adult = false,
        id = 0,
        name = "Willem Dafoe",
        originalName = "Willem Dafoe",
        mediaType = "person",
        popularity = 3.14f,
        gender = 0,
        knownForDepartment = "acting",
        profilePath = null,
        knownFor = emptyList()
    )

    override fun build(): SearchMultiResultPerson {
        return data.copy()
    }

    fun withName(name: String) = apply { data = data.copy(name = name) }
}