package com.ajdi.yassin.popularmovies.data.remote.paging;

import com.ajdi.yassin.popularmovies.data.remote.api.MovieService;
import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesFilterType;

import java.util.concurrent.Executor;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * A simple data source factory provides a way to observe the last created data source.
 *
 * @author Yassin Ajdi.
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    public MutableLiveData<MoviePageKeyedDataSource> sourceLiveData = new MutableLiveData<>();

    private final MovieService movieService;
    private final Executor networkExecutor;
    private final MoviesFilterType sortBy;

    public MovieDataSourceFactory(MovieService movieService,
                                  Executor networkExecutor, MoviesFilterType sortBy) {
        this.movieService = movieService;
        this.sortBy = sortBy;
        this.networkExecutor = networkExecutor;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MoviePageKeyedDataSource movieDataSource =
                new MoviePageKeyedDataSource(movieService, networkExecutor, sortBy);
        sourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
