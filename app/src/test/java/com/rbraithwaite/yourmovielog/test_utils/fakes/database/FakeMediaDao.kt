package com.rbraithwaite.yourmovielog.test_utils.fakes.database

import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import org.mockito.kotlin.mock

class FakeMediaDao(
    private val database: FakeDatabase
): MediaDao() {
    val mock: MediaDao = mock()

    override suspend fun insertMovie(movie: MediaMovieEntity) {
        mock.insertMovie(movie)

        database.insert(movie, MediaMovieEntityIdSelector())
    }
}

// TODO [25-12-2 5:48p.m.] FakeDatabase needs to better deal with non-integer, non-incrementing ids.
class MediaMovieEntityIdSelector: IdSelector<MediaMovieEntity, String> {
    override fun getId(entity: MediaMovieEntity): String = entity.uuid

    override fun updateId(
        entity: MediaMovieEntity,
        newId: String
    ): MediaMovieEntity {
        return entity.copy(uuid = newId)
    }

    override fun isNewEntity(entity: MediaMovieEntity): Boolean = false

    override fun incrementId(id: String): String = id
}