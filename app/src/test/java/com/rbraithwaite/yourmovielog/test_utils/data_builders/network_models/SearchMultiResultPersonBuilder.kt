package com.rbraithwaite.yourmovielog.test_utils.data_builders.network_models

import com.rbraithwaite.test_data_utils.Builder
import com.rbraithwaite.yourmovielog.data.network.models.Person

fun aPerson(
    buildBlock: PersonBuilder.() -> Unit = {}
): PersonBuilder {
    return PersonBuilder().apply(buildBlock)
}

class PersonBuilder: Builder<Person> {
    private var data = Person(
        adult = false,
        id = 0,
        name = "Willem Dafoe",
        originalName = "Willem Dafoe",
        popularity = 3.14f,
        gender = 0,
        knownForDepartment = "acting",
        profilePath = null,
        knownFor = emptyList()
    )

    override fun build(): Person {
        return data.copy()
    }

    fun withName(name: String) = apply { data = data.copy(name = name) }
}