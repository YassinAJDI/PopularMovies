package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * A simple data source factory provides a way to observe the last created data source.
 *
 * @author Yassin Ajdi.
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    public MutableLiveData<MoviePageKeyedDataSource> sourceLiveData = new MutableLiveData<>();

    private final MovieApiService movieApiService;
    private final MoviesFilterType sortBy;

    public MovieDataSourceFactory(MovieApiService movieApiService, MoviesFilterType sortBy) {
        this.movieApiService = movieApiService;
        this.sortBy = sortBy;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MoviePageKeyedDataSource movieDataSource = new MoviePageKeyedDataSource(movieApiService, sortBy);
        sourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
