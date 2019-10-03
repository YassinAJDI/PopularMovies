package com.ajdi.yassin.popularmovies.data.local.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yassin Ajdi.
 */
data class MoviesResponse (

    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("total_results")
    var totalResults: Int = 0,

    @SerializedName("total_pages")
    var totalPages: Int = 0,

    @SerializedName("results")
    var movies: List<Movie>? = null
)
