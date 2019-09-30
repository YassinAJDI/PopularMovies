package com.ajdi.yassin.popularmovies.data.local.model

import com.google.gson.annotations.SerializedName
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

/**
 * @author Yassin Ajdi.
 */
@Entity(tableName = "trailer",
        foreignKeys = [ForeignKey(entity = Movie::class,
                parentColumns = ["id"],
                childColumns = ["movie_id"],
                onDelete = CASCADE,
                onUpdate = CASCADE)],
        indices = [Index(value = ["movie_id"])])
data class Trailer (

    @PrimaryKey
    @SerializedName("id")
    var id: String,

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,

    @SerializedName("key")
    var key: String? = null,

    @SerializedName("site")
    var site: String? = null,

    @SerializedName("name")
    var title: String? = null
)
