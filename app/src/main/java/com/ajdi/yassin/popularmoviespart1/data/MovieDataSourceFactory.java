package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.api.MovieApiService;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * A simple data source factory provides a way to observe the last created data source.
 *
 * @author Yassin Ajdi.
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MutableLiveData<MoviePageKeyedDataSource> sourceLiveData = new MutableLiveData<>();

    private final MovieApiService movieApiService;

    public MovieDataSourceFactory(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MoviePageKeyedDataSource movieDataSource = new MoviePageKeyedDataSource(movieApiService);
        sourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
