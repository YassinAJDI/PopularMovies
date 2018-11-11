package com.ajdi.yassin.popularmoviespart1.data;

import com.ajdi.yassin.popularmoviespart1.data.local.MoviesLocalDataSource;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMovieDetailsResult;
import com.ajdi.yassin.popularmoviespart1.data.model.RepoMoviesResult;
import com.ajdi.yassin.popularmoviespart1.data.model.Resource;
import com.ajdi.yassin.popularmoviespart1.data.remote.MoviesRemoteDataSource;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.ApiResponse;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import timber.log.Timber;

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

    public LiveData<Resource<Movie>> load(final long movieId) {
        return new NetworkBoundResource<Movie, Movie>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull Movie item) {
                mLocalDataSource.saveMovie(item);
                Timber.d("Movie added to database");
            }

            @Override
            protected boolean shouldFetch(@Nullable Movie data) {
                return data == null; // only fetch fresh data if it doesn't exist in database
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                Timber.d("Loading movie from database");
                return mLocalDataSource.getMovieById(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                Timber.d("Downloading movie from network");
                return mRemoteDataSource.loadMovie(movieId);
            }

            @NonNull
            @Override
            protected void onFetchFailed() {
                // ignored
                Timber.d("Fetch failed!!");
            }
        }.getAsLiveData();
    }

    @Override
    public RepoMovieDetailsResult loadMovie(final long movieId) {
        final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        final MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();
        // Show loading bar to user
//        networkState.setValue(NetworkState.LOADING);
//        // check if movie exist in local database
//        mExecutors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                Movie movie = mLocalDataSource.getMovieById(movieId);
//                if (movie != null) {
//                    // awesome, movie exist in database. show movie details
//                    networkState.postValue(NetworkState.LOADED);
//                    movieLiveData.postValue(movie);
//                } else { // movie doesn't exist, fetch movie from network
//                    mExecutors.networkIO().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Response<Movie> movieResponse = mRemoteDataSource.loadMovie(movieId);
//                                Movie loadedMovie = movieResponse.body();
//                                // insert movie into database
//                                mLocalDataSource.saveMovie(loadedMovie);
//                                // TODO: 11/9/2018 insert trailers and reviews
//                                // finished loading, show movie details
//                                networkState.postValue(NetworkState.LOADED);
//                                movieLiveData.postValue(loadedMovie);
//                            } catch (IOException e) {
//                                // handle network exceptions(in case of no internet access)
//                                NetworkState error = NetworkState.error(e.getMessage());
//                                networkState.postValue(error);
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });

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
                Timber.d("Adding movie to favorites");
                mLocalDataSource.favoriteMovie(movie);
            }
        });
    }

    @Override
    public void unfavoriteMovie(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Removing movie from favorites");
                mLocalDataSource.unfavoriteMovie(movie);
            }
        });
    }
}
