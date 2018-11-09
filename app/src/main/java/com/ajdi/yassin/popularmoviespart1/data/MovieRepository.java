package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.local.MoviesLocalDataSource;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.data.remote.MoviesRemoteDataSource;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;

/**
 * Repository implementation that returns a paginated data and loads data directly from network.
 *
 * @author Yassin Ajdi.
 */
public class MovieRepository implements DataSource {

    private static volatile MovieRepository sInstance;

    private final MoviesLocalDataSource mLocalDataSource;

    private final MoviesRemoteDataSource mRemoteDataSource;

    private final AppExecutors mExecutors;

    private MovieRepository(MoviesLocalDataSource localDataSource,
                            MoviesRemoteDataSource remoteDataSource,
                            AppExecutors executors) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    public static MovieRepository getInstance(MoviesLocalDataSource localDataSource,
                                              MoviesRemoteDataSource remoteDataSource,
                                              AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MovieRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepository(localDataSource, remoteDataSource, executors);
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
        // check if movie exist in local database
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Movie movie = mLocalDataSource.getMovieById(movieId);
                if (movie != null) {
                    // awesome, movie exist in database. show movie details
                    networkState.postValue(NetworkState.LOADED);
                    movieLiveData.postValue(movie);
                } else { // movie doesn't exist, lets fetch movie from network
                    mExecutors.networkIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Response<Movie> movieResponse = mRemoteDataSource.loadMovie(movieId);
                                Movie movie = movieResponse.body();
                                // insert movie into database
                                mLocalDataSource.saveMovie(movie);
                                // TODO: 11/9/2018 insert trailers and reviews
                                // finished loading, show movie details
                                networkState.postValue(NetworkState.LOADED);
                                movieLiveData.postValue(movie);
                            } catch (IOException e) {
                                // handle network exceptions(in case of no internet access)
                                NetworkState error = NetworkState.error(e.getMessage());
                                networkState.postValue(error);
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        return new RepoMovieDetailsResult(movieLiveData, networkState);
    }

    @Override
    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType sortBy) {
        return mRemoteDataSource.loadMoviesFilteredBy(sortBy);
    }

    @Override
    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mLocalDataSource.getAllFavoriteMovies();
    }

    @Override
    public void favoriteMovie(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLocalDataSource.favoriteMovie(movie);
            }
        });
    }

    @Override
    public void unfavoriteMovie(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLocalDataSource.unfavoriteMovie(movie);
            }
        });
    }
}
