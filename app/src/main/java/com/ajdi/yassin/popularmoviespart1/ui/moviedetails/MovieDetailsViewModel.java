package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;

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

    private LiveData<RepoMovieDetailsResult> resultLiveData;

    private MutableLiveData<Long> movieId = new MutableLiveData<>();

    private LiveData<Movie> movieLiveData;

    private LiveData<NetworkState> networkState;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
        resultLiveData = Transformations.map(movieId, new Function<Long, RepoMovieDetailsResult>() {
            @Override
            public RepoMovieDetailsResult apply(Long input) {
                return repository.getMovie(input);
            }
        });
        movieLiveData = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<Movie>>() {
                    @Override
                    public LiveData<Movie> apply(RepoMovieDetailsResult input) {
                        return input.data;
                    }
                });
        networkState = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(RepoMovieDetailsResult input) {
                        return input.networkState;
                    }
                });
    }

    LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    void setMovieId(long movieId) {
        this.movieId.setValue(movieId);
    }

    void retry(long movieId) {
        setMovieId(movieId);
    }
}
