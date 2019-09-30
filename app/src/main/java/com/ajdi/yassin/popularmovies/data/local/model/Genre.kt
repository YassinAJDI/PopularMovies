package com.ajdi.yassin.popularmovies.data.local.model

import com.google.gson.annotations.SerializedName

/**
 * @author Yassin Ajdi
 * @since 11/20/2018.
 */
data class Genre (

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String? = null
)
