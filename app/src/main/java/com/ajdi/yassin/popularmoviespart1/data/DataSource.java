package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;

/**
 * @author Yassin Ajdi.
 */
public interface DataSource {

    RepoMovieDetailsResult getMovie(long movieId);

    RepoMoviesResult getFilteredMoviesBy(MoviesFilterType sortBy);
}
