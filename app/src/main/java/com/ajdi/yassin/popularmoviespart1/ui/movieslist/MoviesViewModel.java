package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class MoviesViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    private LiveData<PagedList<Movie>> pagedList;

    public MoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        pagedList = movieRepository.getPopularMovies();
    }

    public LiveData<PagedList<Movie>> getPagedList() {
        return pagedList;
    }
}