package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * @author Yassin Ajdi.
 */
public class MovieDetailsViewModel extends ViewModel {

    private final MovieRepository repository;

    private MutableLiveData<Long> movieId;

    private LiveData<Movie> movieLiveData;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
        movieLiveData = Transformations.switchMap(movieId, new Function<Long, LiveData<Movie>>() {
            @Override
            public LiveData<Movie> apply(Long input) {
                return repository.getMovie(input);
            }
        });
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    void setMovieId(long movieId) {
        this.movieId.setValue(movieId);
    }
}
