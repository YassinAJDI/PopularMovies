package com.ajdi.yassin.popularmovies.data.local.model

import com.google.gson.annotations.SerializedName

/**
 * @author Yassin Ajdi
 * @since 11/13/2018.
 */
data class ReviewsResponse (

    @SerializedName("results")
    var reviews: List<Review>? = null
)
