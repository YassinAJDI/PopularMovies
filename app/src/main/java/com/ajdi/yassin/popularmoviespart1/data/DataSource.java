package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;

import androidx.lifecycle.MutableLiveData;

/**
 * @author Yassin Ajdi.
 */
public interface DataSource {

    MutableLiveData<Movie> getMovie(long movieId);

    RepoMoviesResult getFilteredMoviesBy(MoviesFilterType sortBy);
}
