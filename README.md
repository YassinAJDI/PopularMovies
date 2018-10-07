# Popular Movies Stage 1
Popular Movies is created as a part of Udacity Android Developer Nanodegree Program, is an app which helps you to discover the latest popular and top rated movies. You can flip through movie posters, check movie details. The application fetches movie data using themoviedb.org API.

## Features:
* Discover the most popular and the most rated movies
* MVVM with Android Architecture Components
* Pagination and endless scrolling using Android paging library.
* Handle network status and network failures
* Material design.

## Screenshots

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Android Studio 3.2 stable
* Java JDK

### Installing

Follow this steps if you want get a local copy of the project in your machine.

##### 1. Clone or fork the repository by running the cammand below.
	
```
git https://github.com/YassinAJDI/popular-movies-part1.git
```

##### 2. Import the project in AndroidStudio, and add API Key
1. In Android Studio, go to File -> New -> Import project
2. Follew the dialog wizard to choose the folder where you cloned the project and click on open.
3. Androidstudio imports the projects and builds it for you.
4. Add TheMovieDb API Key inside `gradle.properties` file.

```
TMDB_API_KEY="Your API Key here"
```

## Libraries
* [AndroidX](https://developer.android.com/jetpack/androidx/) - Previously known as 'Android support Library'
* [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling 
* [Retrofit 2](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc. 
* [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Paging](https://developer.android.com/topic/libraries/architecture/paging/) - The Paging Library makes it easier for you to load data gradually and gracefully
* [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
* [OkHttp](https://github.com/square/okhttp)

## License



