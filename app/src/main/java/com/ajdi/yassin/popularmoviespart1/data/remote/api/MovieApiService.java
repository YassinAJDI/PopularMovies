package com.ajdi.yassin.popularmoviespart1.data.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * TheMovieDB API communication setup via Retrofit.
 * <p>
 * Created by Yassin Ajdi.
 */
public interface MovieApiService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies();

}
