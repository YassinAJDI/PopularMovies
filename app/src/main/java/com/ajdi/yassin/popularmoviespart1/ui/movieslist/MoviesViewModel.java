package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

/**
 * @author Yassin Ajdi.
 */
public class MoviesViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    private RepoMoviesResult repoMoviesResult;

    public MoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;

        repoMoviesResult = movieRepository.getPopularMovies();
    }

    LiveData<PagedList<Movie>> getPagedList() {
        return repoMoviesResult.data;
    }

    LiveData<NetworkState> getNetWorkState() {
        return repoMoviesResult.networkState;
    }

    // retries any failed requests.
    void retry() {
        repoMoviesResult.sourceLiveData.getValue().retryCallback.invoke();
    }
}