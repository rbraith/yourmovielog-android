package com.rbraithwaite.yourmovielog

import com.rbraithwaite.yourmovielog.core.data.ReviewDate
import kotlin.random.Random
import kotlin.random.nextInt

object TempWipData {
    // TODO [24-02-2 12:17a.m.] broken.
//    val allReviews: List<Review> by lazy {
//        generateMediaReviews(
//            count = 50,
//            extras = setOf(Review.Extras.RelatedMedia::class)
//        )
//    }

    // TODO [24-02-2 12:17a.m.] broken.
//    val allCustomMedia: List<CustomMovie> by lazy {
//        buildList {
//            repeat(20) {
//                add(generateCustomMedia())
//            }
//        }
//    }

    // TODO [24-02-2 12:17a.m.] broken.
//    val allTmdbLiteMedia: List<TmdbLite> by lazy {
//        generateTmdbLiteMedia(
//            movies = 40
//        )
//    }
}

class IntCounter(private var value: Int = 0) {
    fun increment(): Int {
        value++
        return value
    }
}

class LongCounter(private var value: Long = 0L) {
    fun increment(): Long {
        value++
        return value
    }
}

private val customMediaId = LongCounter()
private val tmdbLiteMovieId = LongCounter()
private val mediaReviewId = IntCounter()

// TODO [24-02-2 12:17a.m.] broken.
//private fun generateCustomMedia(): CustomMovie {
//    return CustomMovie(
//        id = customMediaId.increment(),
//        title = RandomText.generateByWordCount(min = 2, max = 5)
//    )
//}

// TODO [24-02-2 12:17a.m.] broken.
//private fun generateTmdbLiteMedia(movies: Int): List<TmdbLite> {
//    val moviesList: List<TmdbLite.Movie> = buildList {
//        repeat(movies) {
//            add(generateTmdbLiteMovie())
//        }
//    }
//
//    // TODO [24-01-6 10:52p.m.] -- just adding movies for now, later randomly mix with other data
//    //  (tv shows, people).
//    return moviesList
//}

// TODO [24-02-2 12:17a.m.] broken.
//private fun generateTmdbLiteMovie(): TmdbLite.Movie {
//    return TmdbLite.Movie(
//        id = tmdbLiteMovieId.increment(),
//        title = RandomText.generateByWordCount(min = 2, max = 5),
//        overview = RandomText.generateByWordCount(min = 12, max = 24),
//        posterPath = null,
//        genreIds = emptyList(),
//        popularity = RANDOM.nextFloat() * 100.0f,
//        releaseDate = null,
//        voteAverage = RANDOM.nextFloat() * 10.0f,
//        voteCount = RANDOM.nextInt(from = 1000, until = 5000)
//    )
//}

// TODO [24-02-2 12:17a.m.] broken.
//private fun generateMediaReviews(count: Int, extras: Set<MediaReviewExtrasType>): List<Review> {
//    return buildList {
//        repeat(count) {
//            var mediaReview = generateMediaReview()
//            for (extrasType in extras) {
//                when (extrasType) {
//                    Review.Extras.RelatedMedia::class -> {
//                        mediaReview = mediaReview.withExtras(generateRandomMediaReviewRelatedMedia())
//                    }
//                }
//            }
//            add(mediaReview)
//        }
//    }
//}

// TODO [24-02-2 12:17a.m.] broken.
//private fun generateRandomMediaReviewRelatedMedia(): Review.Extras.RelatedMedia {
//    val isCustomMedia = RANDOM.nextFloat() <= 0.25f
//    if (isCustomMedia) {
//        return Review.Extras.RelatedMedia.Custom(data = TempWipData.allCustomMedia.random())
//    }
//    return Review.Extras.RelatedMedia.Tmdb(data = TempWipData.allTmdbLiteMedia.random())
//}

/**
 * Generate MediaReview without extras.
 */
// TODO [24-02-2 12:17a.m.] broken.
//private fun generateMediaReview(): Review {
//    return Review(
//        id = mediaReviewId.increment(),
//        rating = nullable(0.2f) { RANDOM.nextInt(0..100) },
//        review = nullable(0.2f) {
//            RandomText.generateByWordCount(min = 5, max = 20)
//        },
//        reviewDate = nullable(0.2f) {
//            generateRandomReviewDate()
//        },
//        watchContext = nullable(0.4f) {
//            RandomText.randomWord()
//        }
//    )
//}

private fun generateRandomReviewDate(): ReviewDate {
    var reviewDate = ReviewDate(
        year = RANDOM.nextInt(1991..2023),
        null,
        null
    )

    reviewDate = reviewDate.copy(
        month = nullable(0.3f) {
            RANDOM.nextInt(0..11)
        }
    )

    if (reviewDate.month != null) {
        reviewDate = reviewDate.copy(
            day = nullable(0.3f) {
                RANDOM.nextInt(1..28) // valid date no matter the month
            }
        )
    }

    return reviewDate
}

// REFACTOR [24-01-6 4:42p.m.] -- I can move these generic test data utilties to a module.
private const val SEED = 1
private val RANDOM = Random(SEED)

private fun <T> nullable(probabilityOfNull: Float, nonNullValue: () -> T): T? {
    val nullFloat = RANDOM.nextFloat()
    if (nullFloat <= probabilityOfNull) {
        return null
    }
    return nonNullValue()
}

object RandomText {
    private val loremIpsumParagraphs = listOf(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nec metus odio. Duis posuere ut orci nec consectetur. Sed porttitor vel nisl ut malesuada. Proin sit amet erat et ipsum volutpat rhoncus. Duis blandit tellus tristique, tristique orci quis, efficitur magna. Suspendisse interdum sapien ante, pellentesque venenatis tellus varius vitae. Ut vel massa molestie, aliquet felis at, feugiat tellus. Etiam sollicitudin nulla nisi, quis feugiat justo semper quis. Praesent varius elit non porttitor aliquam. Duis volutpat convallis ornare. Quisque eu accumsan magna, id convallis ligula. Nam nisi dolor, commodo eu ornare sit amet, posuere in erat. Nunc eu odio quis nunc congue semper.",
        "Integer placerat metus sed ligula viverra tempor. Nam et odio posuere, tempus erat quis, iaculis ligula. Vestibulum fringilla malesuada elit vel mattis. Quisque tincidunt ante tincidunt rhoncus aliquam. Ut aliquam dui et quam tincidunt molestie. Donec cursus quam lacus, in facilisis sem egestas eu. Duis mollis ipsum eget nibh molestie aliquet. Curabitur sollicitudin mi quis erat luctus sodales. In ultrices ultrices imperdiet. Nam luctus est sed enim venenatis, at auctor est pulvinar. Nullam vel dui felis. Pellentesque varius leo sapien, luctus ornare justo auctor id. Sed pretium nisi venenatis, pretium massa nec, ullamcorper lectus.",
        "Integer id quam sed nulla posuere cursus ut facilisis leo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam auctor vel est at laoreet. Pellentesque vitae venenatis odio. Curabitur porttitor erat id nunc porttitor, eget luctus sapien semper. Sed nisi lacus, molestie non arcu eu, posuere tristique nisl. Donec faucibus, nisi eget condimentum fringilla, nunc augue ullamcorper ante, fringilla venenatis lacus metus a enim. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit amet mi hendrerit ligula tempor pulvinar et ac magna. Donec fermentum, turpis vel efficitur bibendum, massa neque fringilla est, sed vestibulum ligula massa non justo. Sed pulvinar, nulla eu placerat lacinia, quam lorem posuere neque, vitae finibus tellus quam sed augue. Aliquam at eros et nisl mollis commodo nec malesuada magna. Proin ornare lorem non purus pellentesque, vitae finibus augue porta. Donec tincidunt metus quis diam pulvinar vestibulum.",
        "Etiam tincidunt diam et elementum placerat. Sed vel finibus mauris. Curabitur nibh nisi, iaculis ut accumsan ut, laoreet id libero. Nam mollis nulla vitae elit efficitur, eu efficitur quam tempus. Suspendisse potenti. Cras risus sem, ultrices venenatis nisi lobortis, feugiat condimentum quam. Phasellus in congue urna, vel luctus nisl. Nunc et semper lorem. In cursus, mi id maximus finibus, mi libero suscipit metus, non posuere dolor libero in justo. Fusce lacus leo, mollis at odio vitae, consequat ultrices lorem. Nullam at purus dictum, consectetur ipsum tempus, convallis lorem. Suspendisse potenti. Nullam pulvinar est eu pellentesque cursus. Sed vel ex metus. Morbi at erat cursus, rutrum ligula tristique, congue lorem. Phasellus in nibh congue velit maximus bibendum."
    )

    private val loremIpsumWords by lazy {
        loremIpsumParagraphs.flatMap { it.split(" ") }
    }

    fun generateByWordCount(min: Int = 0, max: Int): String {
        val countToTake = RANDOM.nextInt(min..max)
        if (countToTake <= 0) {
            return ""
        }
        return buildList {
            val loremWordsSize = loremIpsumWords.size

            var index = RANDOM.nextInt(until = loremWordsSize) // pick random starting point, for better results
            var wordsAdded = 0

            while (wordsAdded < countToTake) {
                // taking the mod since index can be greater than the words size
                add(loremIpsumWords[index % loremWordsSize])

                index++
                wordsAdded++
            }
        }.joinToString(" ")
    }

    fun randomWord(): String {
        return loremIpsumWords.random()
    }
}

