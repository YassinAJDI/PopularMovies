package com.ajdi.yassin.popularmovies.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.MoviesResponse;
import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.data.remote.api.MovieService;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesFilterType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A data source that uses the before/after keys returned in page requests.
 * <p>
 *
 * @author Yassin Ajdi.
 */
public class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;

    public MutableLiveData<Resource> networkState = new MutableLiveData<>();

    private final MovieService movieService;

    private final Executor networkExecutor;

    private final MoviesFilterType sortBy;

    public RetryCallback retryCallback = null;

    public MoviePageKeyedDataSource(MovieService movieService,
                                    Executor networkExecutor, MoviesFilterType sortBy) {
        this.movieService = movieService;
        this.networkExecutor = networkExecutor;
        this.sortBy = sortBy;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));

        // load data from API
        Call<MoviesResponse> request;
        if (sortBy == MoviesFilterType.POPULAR) {
            request = movieService.getPopularMovies(FIRST_PAGE);
        } else if (sortBy == MoviesFilterType.TOP_RATED){
            request = movieService.getTopRatedMovies(FIRST_PAGE);
        } else {
            request = movieService.getNowPlayingMovies(FIRST_PAGE);
        }

        // we execute sync since this is triggered by refresh
        try {
            Response<MoviesResponse> response = request.execute();
            MoviesResponse data = response.body();
            List<Movie> movieList =
                    data != null ? data.getMovies() : Collections.<Movie>emptyList();

            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(movieList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            // publish error
            retryCallback = new RetryCallback() {
                @Override
                public void invoke() {
                    networkExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            loadInitial(params, callback);
                        }
                    });

                }
            };
            networkState.postValue(Resource.error(e.getMessage(), null));
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Movie> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));

        // load data from API
        Call<MoviesResponse> request;
        if (sortBy == MoviesFilterType.POPULAR) {
            request = movieService.getPopularMovies(params.key);
        } else if (sortBy == MoviesFilterType.TOP_RATED) {
            request = movieService.getTopRatedMovies(params.key);
        } else {
            request = movieService.getNowPlayingMovies(params.key);
        }

        request.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse data = response.body();
                    List<Movie> movieList =
                            data != null ? data.getMovies() : Collections.<Movie>emptyList();

                    retryCallback = null;
                    callback.onResult(movieList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = new RetryCallback() {
                        @Override
                        public void invoke() {
                            loadAfter(params, callback);
                        }
                    };
                    networkState.postValue(Resource.error("error code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                retryCallback = new RetryCallback() {
                    @Override
                    public void invoke() {
                        networkExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                loadAfter(params, callback);
                            }
                        });
                    }
                };
                networkState.postValue(Resource.error(t != null ? t.getMessage() : "unknown error", null));
            }
        });
    }

    public interface RetryCallback {
        void invoke();
    }

}
