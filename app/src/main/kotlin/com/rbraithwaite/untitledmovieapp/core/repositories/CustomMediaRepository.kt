package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia

interface CustomMediaRepository {
    suspend fun addOrUpdateCustomMedia(vararg customMedia: CustomMedia)

    suspend fun findMedia(searchCriteria: String): List<CustomMedia>
}