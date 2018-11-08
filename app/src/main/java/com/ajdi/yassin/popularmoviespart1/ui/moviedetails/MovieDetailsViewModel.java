package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
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

    private boolean isFavorite;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
        resultLiveData = Transformations.map(movieId, new Function<Long, RepoMovieDetailsResult>() {
            @Override
            public RepoMovieDetailsResult apply(Long movieId) {
                return repository.loadMovie(movieId);
            }
        });
        movieLiveData = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<Movie>>() {
                    @Override
                    public LiveData<Movie> apply(RepoMovieDetailsResult result) {
                        if (result.data.getValue() != null) {
                            isFavorite = result.data.getValue().isFavorite();
                        }
                        return result.data;
                    }
                });
        networkState = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(RepoMovieDetailsResult result) {
                        return result.networkState;
                    }
                });
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setMovieId(long movieId) {
        this.movieId.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieId(movieId);
    }

    public void onFavoriteClicked() {
        Movie movie = movieLiveData.getValue();
        if (!isFavorite) {
            repository.favoriteMovie(movie);
            isFavorite = true;
        } else {
            repository.unfavoriteMovie(movie);
            isFavorite = false;
        }
    }
}
