package com.ajdi.yassin.popularmoviespart1.data.local;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.data.model.MovieAndTrailers;
import com.ajdi.yassin.popularmoviespart1.data.model.Trailer;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;
import timber.log.Timber;

/**
 * @author Yassin Ajdi.
 */
public class MoviesLocalDataSource {

    private static volatile MoviesLocalDataSource sInstance;

    private final MoviesDatabase mDatabase;

    private MoviesLocalDataSource(MoviesDatabase database) {
        mDatabase = database;
    }

    public static MoviesLocalDataSource getInstance(MoviesDatabase database) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesLocalDataSource(database);
                }
            }
        }
        return sInstance;
    }

    public void saveMovie(Movie movie) {
        mDatabase.moviesDao().insertMovie(movie);
        insertTrailers(movie.getTrailersResponse().getTrailers(), movie.getId());
    }

    private void insertTrailers(List<Trailer> trailers, long movieId) {
        for (Trailer trailer : trailers) {
            trailer.setMovieId(movieId);
        }
        mDatabase.trailersDao().insertAllTrailers(trailers);
        Timber.d("%s trailers inserted into database.", trailers.size());
    }

    public LiveData<Movie> getMovieById(long movieId) {
        return mDatabase.moviesDao().getMovieById(movieId);
    }

    public LiveData<MovieAndTrailers> getMovie(long movieId) {
        Timber.d("Loading movie and trailers");
        return mDatabase.moviesDao().getMovie(movieId);
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mDatabase.moviesDao().getAllFavoriteMovies();
    }

    public void favoriteMovie(Movie movie) {
        mDatabase.moviesDao().favoriteMovie(movie.getId());
    }

    public void unfavoriteMovie(Movie movie) {
        mDatabase.moviesDao().unFavoriteMovie(movie.getId());
    }
}
