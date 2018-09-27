package com.ajdi.yassin.popularmoviespart1.data.remote;

import com.ajdi.yassin.popularmoviespart1.data.remote.model.Movie;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * @author Yassin Ajdi.
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    MutableLiveData<MoviePageKeyedDataSource> moviesDataSourceLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Movie> create() {
        return null;
    }
}
