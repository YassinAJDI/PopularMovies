package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public interface DataSource {

    LiveData<PagedList<Movie>> getPopularMovies();
}
