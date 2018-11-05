package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.data.remote.MoviesRemoteDataSource;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import androidx.lifecycle.MutableLiveData;

/**
 * Repository implementation that returns a paginated data and loads data directly from network.
 *
 * @author Yassin Ajdi.
 */
public class MovieRepository implements DataSource {

    private static volatile MovieRepository sInstance;

    private final MoviesRemoteDataSource mRemoteDataSource;

    private final AppExecutors mExecutors;

    private MovieRepository(MoviesRemoteDataSource remoteDataSource,
                            AppExecutors executors) {
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    public static MovieRepository getInstance(MoviesRemoteDataSource remoteDataSource,
                                              AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MovieRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepository(remoteDataSource, executors);
                }
            }
        }
        return sInstance;
    }

    @Override
    public RepoMovieDetailsResult loadMovie(final long movieId) {
        final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        final MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();
        // Show loading bar to user
        networkState.setValue(NetworkState.LOADING);
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                RepoMovieDetailsResult result = mRemoteDataSource.loadMovie(movieId);
                Movie movie = result.data.getValue();
                if (movie != null) {
                    // finished loading
                    movieLiveData.postValue(movie);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    // handle network exceptions(in case of no internet access)
                    NetworkState error = result.networkState.getValue();
                    networkState.postValue(error);
                }
            }
        });
        return new RepoMovieDetailsResult(movieLiveData, networkState);
    }

    @Override
    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy) {
        return mRemoteDataSource.loadMoviesFilteredBy(sortBy);
    }
}
