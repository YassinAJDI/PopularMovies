package com.ajdi.yassin.popularmovies.ui.moviedetails;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.MovieRepository;
import com.ajdi.yassin.popularmovies.data.local.model.MovieAndTrailers;
import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.utils.SnackbarMessage;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

/**
 * @author Yassin Ajdi.
 */
public class MovieDetailsViewModel extends ViewModel {

    private final MovieRepository repository;

    private LiveData<Resource<MovieAndTrailers>> result;

    private MutableLiveData<Long> movieIdLiveData = new MutableLiveData<>();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private boolean isFavorite;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
    }

    public void init(long movieId) {
        if (result != null) {
            return; // trigger loading movie details, only once the activity created
        }
        Timber.d("Initializing viewModel");

        result = Transformations.switchMap(movieIdLiveData,
                new Function<Long, LiveData<Resource<MovieAndTrailers>>>() {
                    @Override
                    public LiveData<Resource<MovieAndTrailers>> apply(Long movieId) {
                        return repository.load(movieId);
                    }
                });

        setMovieIdLiveData(movieId);
    }

    public LiveData<Resource<MovieAndTrailers>> getResult() {
        return result;
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieIdLiveData(movieId);
    }

    public void onFavoriteClicked() {
        MovieAndTrailers movieAndTrailers = result.getValue().data;
        if (!isFavorite) {
            repository.favoriteMovie(movieAndTrailers.movie);
            isFavorite = true;
            showSnackbarMessage(R.string.movie_added_successfully);
        } else {
            repository.unfavoriteMovie(movieAndTrailers.movie);
            isFavorite = false;
            showSnackbarMessage(R.string.movie_removed_successfully);
        }
    }

    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }
}
