package com.rbraithwaite.untitledmovietracker.test_utils.fakes.database

import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import org.mockito.kotlin.mock

class FakeCustomMediaDao(
    private val database: FakeDatabase
): CustomMediaDao() {
    val mock = mock<CustomMediaDao>()

    override suspend fun insertOrUpdateCustomMovies(vararg customMovies: CustomMovieEntity): List<Long> {
        mock.insertOrUpdateCustomMovies(*customMovies)
        return database.insertOrUpdateMultiple(customMovies.toList(), CustomMovieEntityIdSelector())
    }

    override suspend fun findCustomMoviesWithIds(customMovieIds: List<Long>): List<CustomMovieEntity> {
        return database.find {
            customMovieIds.contains(id)
        }
    }

    override suspend fun searchCustomMoviesByTitle(searchCriteria: String): List<CustomMovieEntity> {
        return database.find {
            title.contains(searchCriteria)
        }
    }
}

// REFACTOR [24-02-2 10:11p.m.] -- move this.
class CustomMovieEntityIdSelector: LongIdSelector<CustomMovieEntity>() {
    override fun getId(entity: CustomMovieEntity): Long = entity.id

    override fun updateId(entity: CustomMovieEntity, newId: Long): CustomMovieEntity {
        return entity.copy(id = newId)
    }
}