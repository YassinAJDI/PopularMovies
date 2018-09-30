package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class MovieRepository implements DataSource {

    private static final int PAGE_SIZE = 20;

    private LiveData<PagedList<Movie>> moviesList;

    private final MovieApiService mMovieApiService;

    private final AppExecutors mExecutors;

    public MovieRepository(MovieApiService movieApiService,
                           AppExecutors executors) {
        mMovieApiService = movieApiService;
        mExecutors = executors;
    }

    @Override
    public LiveData<PagedList<Movie>> getPopularMovies() {
        MovieDataSourceFactory sourceFactory = new MovieDataSourceFactory(mMovieApiService);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        // Get the paged list
        moviesList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        return moviesList;
    }
}
