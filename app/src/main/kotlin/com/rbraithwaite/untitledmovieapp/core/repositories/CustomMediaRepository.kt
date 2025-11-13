package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia

// TODO [25-10-26 4:40p.m.] deprecated: delete this.
interface CustomMediaRepository {
    suspend fun upsertCustomMedia(vararg customMedia: CustomMedia)

    suspend fun findMedia(searchCriteria: String): List<CustomMedia>
}