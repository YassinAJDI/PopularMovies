package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.model.MoviesResponse;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A data source that uses the before/after keys returned in page requests.
 * <p>
 *
 * @author Yassin Ajdi.
 */
public class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;

    MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
    MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    private final MovieApiService movieApiService;

    public MoviePageKeyedDataSource(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(NetworkState.LOADING);
        initialLoad.postValue(NetworkState.LOADING);

        // load data from API
        // but before that check filtering option
        Call<MoviesResponse> request = movieApiService.getPopularMovies(FIRST_PAGE);

        try {
            Response<MoviesResponse> response = request.execute();
            MoviesResponse data = response.body();
            List<Movie> movieList = data != null ? data.getMovies() : Collections.<Movie>emptyList();

            networkState.postValue(NetworkState.LOADED);
            initialLoad.postValue(NetworkState.LOADED);
            callback.onResult(movieList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            NetworkState error = NetworkState.error(e.getMessage());
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }
}
