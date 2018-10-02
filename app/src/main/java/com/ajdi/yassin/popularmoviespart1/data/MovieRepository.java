package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Repository implementation that returns a paginated data and loads data directly from network.
 *
 * @author Yassin Ajdi.
 */
public class MovieRepository implements DataSource {

    private static final int PAGE_SIZE = 20;

    private final MovieApiService mMovieApiService;

    private final AppExecutors mExecutors;

    public MovieRepository(MovieApiService movieApiService,
                           AppExecutors executors) {
        mMovieApiService = movieApiService;
        mExecutors = executors;
    }

    @Override
    public RepoMoviesResult getPopularMovies() {
        MovieDataSourceFactory sourceFactory = new MovieDataSourceFactory(mMovieApiService);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
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
