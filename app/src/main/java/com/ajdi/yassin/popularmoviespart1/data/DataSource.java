package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;

/**
 * @author Yassin Ajdi.
 */
public interface DataSource {

    RepoMoviesResult getFilteredMoviesBy(MoviesFilterType sortBy);
}
