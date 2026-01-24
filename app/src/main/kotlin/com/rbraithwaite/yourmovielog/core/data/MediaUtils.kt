package com.rbraithwaite.yourmovielog.core.data

val Media.isTv: Boolean
    get() {
        return this is TvShow || this is TvShow.Season || this is TvShow.Episode
    }
