package com.ajdi.yassin.popularmovies.data.remote;

import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.RepoMoviesResult;
import com.ajdi.yassin.popularmovies.data.remote.api.ApiResponse;
import com.ajdi.yassin.popularmovies.data.remote.api.MovieService;
import com.ajdi.yassin.popularmovies.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmovies.data.remote.paging.MovieDataSourceFactory;
import com.ajdi.yassin.popularmovies.data.remote.paging.MoviePageKeyedDataSource;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmovies.utils.AppExecutors;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class MoviesRemoteDataSource {

    private static final int PAGE_SIZE = 20;

    private final AppExecutors mExecutors;

    private static volatile MoviesRemoteDataSource sInstance;

    private final MovieService mMovieService;

    private MoviesRemoteDataSource(MovieService movieService,
                                   AppExecutors executors) {
        mMovieService = movieService;
        mExecutors = executors;
    }

    public static MoviesRemoteDataSource getInstance(MovieService movieService,
                                                     AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesRemoteDataSource(movieService, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<ApiResponse<Movie>> loadMovie(final long movieId){
        return mMovieService.getMovieDetails(movieId);
    }

    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), sortBy);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        // Get the paged list
        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<NetworkState> networkState = Transformations.switchMap(sourceFactory.sourceLiveData,
                new Function<MoviePageKeyedDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(MoviePageKeyedDataSource input) {
                        return input.networkState;
                    }
                });

        return new RepoMoviesResult(
                moviesPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }
}
