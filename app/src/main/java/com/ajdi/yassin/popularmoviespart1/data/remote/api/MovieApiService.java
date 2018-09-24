package com.ajdi.yassin.popularmoviespart1.data.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * TheMovieDB API communication setup via Retrofit.
 *
 * Created by Yassin Ajdi.
 */
public interface MovieApiService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies();

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies();

}
