package com.ajdi.yassin.popularmoviespart1.data.remote.api;

import com.ajdi.yassin.popularmoviespart1.data.remote.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yassin Ajdi.
 */
public class MoviesResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> movies;
}
