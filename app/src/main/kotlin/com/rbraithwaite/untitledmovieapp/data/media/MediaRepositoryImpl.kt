package com.rbraithwaite.untitledmovieapp.data.media

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mediaDao: MediaDao,
    private val tmdbApiV3: TmdbApiV3
): MediaRepository {

    override suspend fun addNewCustomMedia(customMedia: CustomMedia) {
        externalScope.launch(coroutineDispatcher) {
            mediaDao.addCustomMedia(customMedia.toNewEntity())
        }.join()
    }

    override suspend fun addNewCustomMediaWithReview(customMedia: CustomMedia, review: MediaReview) {
        externalScope.launch(coroutineDispatcher) {
            val customMediaEntity = customMedia.toNewEntity()
            val mediaReviewEntity = MediaReviewEntity(
                mediaType = "custom",
                rating = review.rating,
                review = review.review,
                reviewDate = review.reviewDate,
                watchContext = review.watchContext
            )

            Timber.d("adding review to db: \n$customMediaEntity \n$mediaReviewEntity")

            mediaDao.addNewCustomMediaWithReview(
                customMediaEntity,
                mediaReviewEntity
            )
        }.join()
    }

    // REFACTOR [23-11-19 3:15p.m.] -- extract the api call to a new repository type TmdbRepository (or
    //  something) and rename it to searchQuick(quickInput)
    //  Make this repo into CustomMediaRepository
    //  then there will probably need to be a SearchQuickUseCase combining CustomMediaRepository
    //  with something like TmdbRepository (since tmdb api returns movie/tv/person data types).
    override suspend fun findMedia(searchCriteria: String): List<SearchResult> {
        val foundCustomMedia = mediaDao.searchCustomMedia(searchCriteria)

        // TODO [23-11-11 11:49p.m.] -- I'll need to figure out what to do about the paging
        //  these results would need to be stored as a variable, and different triggers would send
        //  new requests (changes to the search criteria, then also scrolling down).
        val apiResult = tmdbApiV3.searchMulti(searchCriteria)

        return buildList {
            addAll(foundCustomMedia.map { it.toSearchResult() })

            // TODO [23-12-10 10:49p.m.] -- not handling api failures yet
            //  should handle like this:
            //  - skip adding anything to this return list
            //  - instead of returning a list, return a data class with a list for received errors
            //      which the ui layer can display to the user.
            addAll(apiResult.getOrNull()!!.results.map { searchMultiResult ->
                when (searchMultiResult) {
                    // REFACTOR [23-12-10 10:49p.m.] -- not a big fan of these repeated method names idk.
                    is SearchMultiResult.Movie -> searchMultiResult.toCoreSearchResult()
                    is SearchMultiResult.TvShow -> searchMultiResult.toCoreSearchResult()
                    is SearchMultiResult.Person -> searchMultiResult.toCoreSearchResult()
                }
            })
        }
    }

    private fun CustomMedia.toNewEntity(): CustomMediaEntity {
        return CustomMediaEntity(title = this.title)
    }

    private fun CustomMediaEntity.toSearchResult(): SearchResult.CustomMedia {
        return SearchResult.CustomMedia(id, title)
    }

    private fun SearchMultiResult.Movie.toCoreSearchResult(): SearchResult.TmdbMovie {
        return SearchResult.TmdbMovie(
            id,
            title,
            overview,
            posterPath,
            genreIds,
            popularity,
            parseTmdbDateString(releaseDate),
            voteAverage,
            voteCount
        )
    }

    private fun SearchMultiResult.TvShow.toCoreSearchResult(): SearchResult.TmdbTvShow {
        return SearchResult.TmdbTvShow(
            id,
            name,
            overview,
            posterPath,
            genreIds,
            popularity,
            parseTmdbDateString(firstAirDate),
            voteAverage,
            voteCount
        )
    }

    private fun SearchMultiResult.Person.toCoreSearchResult(): SearchResult.TmdbPerson {
        return SearchResult.TmdbPerson(
            id,
            name,
            popularity,
            gender,
            knownForDepartment,
            profilePath
        )
    }

    // REFACTOR [23-11-19 3:43p.m.] -- should delete this.
    private fun CustomMediaEntity.toCustomMedia(): CustomMedia {
        return CustomMedia(id, title)
    }
}

// REFACTOR [23-11-19 3:56p.m.] -- move this somewhere.
private fun parseTmdbDateString(dateString: String): LocalDate? {
    if (dateString.isEmpty()) {
        return null
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
}