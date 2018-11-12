package com.ajdi.yassin.popularmoviespart1.data.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * @author Yassin Ajdi.
 */
@Entity(tableName = "trailer",
        foreignKeys = @ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ),
        indices = {
                @Index(value = {"movie_id"})
        }
)
public class Trailer {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "movie_id")
    private long movieId;

    @SerializedName("key")
    private String key;

    @SerializedName("site")
    private String site;

    @SerializedName("name")
    private String title;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
