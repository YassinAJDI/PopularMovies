package com.ajdi.yassin.popularmoviespart1.data.remote.api;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Call<MoviesResponse> getTopRatedMovies(@Query("page") int page);

    // Instead of using 2 separate requests we use append_to_response
    // to eliminate duplicate requests and save network bandwidth data
    @GET("movie/{id}?append_to_response=videos")
    Call<Movie> getMovieDetails(@Path("id") long id);
}
