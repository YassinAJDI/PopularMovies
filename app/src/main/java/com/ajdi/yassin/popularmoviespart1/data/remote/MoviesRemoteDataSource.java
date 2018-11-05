package com.ajdi.yassin.popularmoviespart1.data.remote;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.remote.paging.MovieDataSourceFactory;
import com.ajdi.yassin.popularmoviespart1.data.remote.paging.MoviePageKeyedDataSource;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import java.io.IOException;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Response;

/**
 * @author Yassin Ajdi.
 */
public class MoviesRemoteDataSource {

    private static final int PAGE_SIZE = 20;

    private final AppExecutors mExecutors;

    private static volatile MoviesRemoteDataSource sInstance;

    private final MovieApiService mMovieApiService;

    private MoviesRemoteDataSource(MovieApiService movieApiService,
                                   AppExecutors executors) {
        mMovieApiService = movieApiService;
        mExecutors = executors;
    }

    public static MoviesRemoteDataSource getInstance(MovieApiService movieApiService,
                                                     AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesRemoteDataSource(movieApiService, executors);
                }
            }
        }
        return sInstance;
    }

    public Response<Movie> loadMovie(final long movieId) throws IOException {
        return mMovieApiService.getMovieDetails(movieId).execute();
    }

    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieApiService, mExecutors.networkIO(), sortBy);

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
