# 🎬 Your Movie Log (Android App)

### 🚧 Currently in development 🚧

### What is this app?

Your Movie Log is a movie and tv show logging app for Android. Find movies and shows and then review/rate them - very exciting stuff!

I am building this as an exercise in modern Android development, and as a personal tool for keeping track of the movies/shows that I watch and better controlling that data myself.

## Android Tech Stack
- **Language**: Kotlin (Coroutines, Flows)
- **UI**: Jetpack Compose, Material Design 3, Navigation
- **Architecture**: MVVM, Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room
- **Network**: Retrofit, OkHttp
- **Tests**: JUnit, Mockito

## <img alt="TMDB" src="https://www.themoviedb.org/assets/2/v4/logos/v2/blue_short-8e7b30f73a4020692ccca9c88bafe5dcb6f8a62a4c6bc55cd9ba82bb2cd95f6c.svg" width="120">

Your Movie Log is powered by the [TMDB](https://www.themoviedb.org) API. Movie/show details, images, videos, other things. No TMDB account functionality though - you can't log in to this app as it stands or sync with your TMDB account (possibly in the future).

```
This product uses the TMDB API but is not endorsed or certified by TMDB.
```

## Build Instructions
This is a portfolio project and isn't currently available on the Play Store, so if you want to use this app you'll need to build it and install an APK onto your device yourself. You'll need to get your own TMDB API key (see the docs [here](https://developer.themoviedb.org/docs/getting-started)) and add it to the repo first.

### Adding your API key to the repo:
- After you clone this repo, create a new file: `./local/secrets/tmdb_api.properties`
- populate the file like this:
```
TMDB_API_BEARER_TOKEN="<your TMDB API key>"
```

## License
MIT