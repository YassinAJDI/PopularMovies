package com.ajdi.yassin.popularmovies.data.remote.api;

import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.MoviesResponse;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * TheMovieDB REST API access points.
 * <p>
 * Created by Yassin Ajdi.
 */
public interface MovieService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("page") int page);

    // Instead of using 2 separate requests we use append_to_response
    // to eliminate duplicate requests and save network bandwidth
    @GET("movie/{id}?append_to_response=videos,credits")
    LiveData<ApiResponse<Movie>> getMovieDetails(@Path("id") long id);
}
