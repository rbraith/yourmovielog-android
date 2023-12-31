package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.test_data_utils.Builder
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult.Person

fun aSearchMultiResultPerson(
    buildBlock: SearchMultiResultPersonBuilder.() -> Unit = {}
): SearchMultiResultPersonBuilder {
    return SearchMultiResultPersonBuilder().apply(buildBlock)
}

class SearchMultiResultPersonBuilder: Builder<Person> {
    private var data = SearchMultiResult.Person(
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

    override fun build(): SearchMultiResult.Person {
        return data.copy()
    }

    fun withName(name: String) = apply { data = data.copy(name = name) }
}