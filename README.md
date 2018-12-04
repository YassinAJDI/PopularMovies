<h1 align="center">Popular Movies Stage 1 + Stage 2</h1>
<p align="center">
<a href="./LICENSE">
	<img src="https://img.shields.io/github/license/mashape/apistatus.svg" />
</a>
<a class="badge-align" href="https://www.codacy.com/app/YassinAJDI/popular-movies-part1?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YassinAJDI/popular-movies-part1&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/762f1405d7b245d792ff3442b15a2a50"/></a>
<a href="https://codeclimate.com/github/YassinAJDI/popular-movies-part1/maintainability"><img src="https://api.codeclimate.com/v1/badges/c2e41f498389bc3088c9/maintainability" /></a>
</p>
<h4 align="center">
Popular Movies is created as part of Udacity Android Developer Nanodegree Program, is an app which allow users to discover the most popular and top rated movies playing. The application fetches movie data using themoviedb.org API while showcasing Android best practices.
</h4>

<h2 align="center">Screenshots</h2>

![Screen](https://raw.githubusercontent.com/YassinAJDI/PopularMovies/master/screenshots/mockup.png)
<h4 align="center">
<img src="screenshots/Phone%20Screenshot%201.jpg" width=240>
<img src="screenshots/Phone%20Screenshot%202.jpg" width=240>
<img src="screenshots/Phone%20Screenshot%203.jpg" width=240>
</h4>

## Features
*   Discover the most popular and the most rated movies
*   MVVM with Android Architecture Components
*   Pagination and endless scrolling using Android paging library.
*   Handle network status and network failures
*   Material design.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
*   Android Studio 3.2 stable
*   Java JDK

### Installing
Follow this steps if you want get a local copy of the project in your machine.

#### 1. Clone or fork the repository by running the cammand below	
```
git https://github.com/YassinAJDI/PopularMovies.git
```

#### 2. Import the project in AndroidStudio, and add API Key
1.  In Android Studio, go to File -> New -> Import project
2.  Follew the dialog wizard to choose the folder where you cloned the project and click on open.
3.  Androidstudio imports the projects and builds it for you.
4.  Add TheMovieDb API Key inside `gradle.properties` file.

```
TMDB_API_KEY="Your API Key here"
```

## Libraries
*   [AndroidX](https://developer.android.com/jetpack/androidx/) - Previously known as 'Android support Library'
*   [Glide](https://github.com/bumptech/glide) - for loading and caching images 
*   [Retrofit 2](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc. 
*   [Gson](https://github.com/google/gson) - for serialization/deserialization Java Objects into JSON and back
*   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
*   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
*   [Paging](https://developer.android.com/topic/libraries/architecture/paging/)
*   [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
*   [OkHttp](https://github.com/square/okhttp)
*   [Timber](https://github.com/JakeWharton/timber)
*   [CircleImageView](https://github.com/hdodenhof/CircleImageView)
*   [TextDrawable](https://github.com/amulyakhare/TextDrawable)

## License
```
MIT License

Copyright (c) 2018 Yassin Ajdi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
