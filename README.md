![Screen](https://raw.githubusercontent.com/YassinAJDI/PopularMovies/master/screenshots/mockup.png)
<h1 align="center">Popular Movies Stage 1 + Stage 2</h1>
<h4 align="center">
	Discover the most popular and top rated movies playing. Movies data fetched using <a href="https://www.themoviedb.org/">themoviedb.org</a> API.
</h4>
<p align="center">
	<a href="./LICENSE">
		<img src="https://img.shields.io/github/license/mashape/apistatus.svg" />
	</a>
	<a class="badge-align" href="https://www.codacy.com/app/YassinAJDI/PopularMovies?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YassinAJDI/PopularMovies&amp;utm_campaign=Badge_Grade">
		<img src="https://api.codacy.com/project/badge/Grade/7ead85c7910f423d9ecea73041b8dbcf"/>
	</a>
</p>

## ✨ Screenshots
| Main Screen | Demo |  Favorites |
|:-:|:-:|:-:|
| ![Fist](screenshots/Screenshot_1.jpg?raw=true) | ![3](screenshots/demo_gif.gif?raw=true) | ![3](screenshots/Screenshot_2.jpg?raw=true) |
| Movie Details | Trailers |  Reviews |
| ![4](screenshots/Screenshot_3.jpg?raw=true) | ![5](screenshots/Screenshot_5.jpg?raw=true) | ![6](screenshots/Screenshot_4.jpg?raw=true) |

## 🌟 Features
*   Discover the most popular and the most rated movies
*   User can view and play trailers on youtube 
*   Shows a list of reviews for each movie
*   Users can mark a movie as favorite in the details view by tapping a heart icon 
*   Users can share movie trailers with their network
*   Offline support: app makes use of `NetworkBoundResource`, which uses database as the single source of truth
*   Advanced uses of Room
*   MVVM with Android Architecture Components(Room, LiveData, ViewModel)
*   Pagination and endless scrolling using Android paging library.
*   Handle network status and network failures
*   ConstraintLayout(guidelines, barriers... etc)
*   Material design.

## 🚀 Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
*   Android Studio 3.2+
*   Java JDK

### Installing
Follow these steps if you want to get a local copy of the project on your machine.

#### 1. Clone or fork the repository by running the command below	
```
git https://github.com/YassinAJDI/PopularMovies.git
```

#### 2. Import the project in AndroidStudio, and add API Key
1.  In Android Studio, go to File -> New -> Import project
2.  Follew the dialog wizard to choose the folder where you cloned the project and click on open.
3.  Android Studio imports the projects and builds it for you.
4.  Add TheMovieDb API Key inside `gradle.properties` file.

```
TMDB_API_KEY="Your API Key here"
```

## 🤝 How to Contribute
1.  Fork it
2.  Create your feature branch (git checkout -b my-new-feature)
3.  Commit your changes (git commit -am 'Add some feature')
4.  Push to the branch (git push origin my-new-feature)
5.  Create new Pull Request

## 📃 Libraries used
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

## 📝 License
This project is released under the MIT license.
See [LICENSE](./LICENSE) for details.

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
