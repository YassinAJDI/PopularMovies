package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.data.remote.MoviesRemoteDataSource;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import java.io.IOException;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;

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
                try {
                    Response<Movie> movieResponse = mRemoteDataSource.loadMovie(movieId);
                    // finished loading
                    networkState.postValue(NetworkState.LOADED);
                    movieLiveData.postValue(movieResponse.body());
                } catch (IOException e) {
                    // handle network exceptions(in case of no internet access)
                    NetworkState error = NetworkState.error(e.getMessage());
                    networkState.postValue(error);
                    e.printStackTrace();
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
