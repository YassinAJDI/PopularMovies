package com.ajdi.yassin.popularmovies.data.local.model

import com.google.gson.annotations.SerializedName

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
data class CreditsResponse (

    @SerializedName("cast")
    var cast: List<Cast>? = null
)
