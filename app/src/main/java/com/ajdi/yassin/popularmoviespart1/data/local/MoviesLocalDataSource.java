package com.ajdi.yassin.popularmoviespart1.data.local;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.AppExecutors;

/**
 * @author Yassin Ajdi.
 */
public class MoviesLocalDataSource {

    private static volatile MoviesLocalDataSource sInstance;

    private final MoviesDao mMovieDao;

    private MoviesLocalDataSource(MoviesDao moviesDao) {
        mMovieDao = moviesDao;
    }

    public static MoviesLocalDataSource getInstance(MoviesDao moviesDao) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesLocalDataSource(moviesDao);
                }
            }
        }
        return sInstance;
    }

    public void saveMovie(Movie movie) {
        mMovieDao.saveMovie(movie);
    }

    public void favoriteMovie(Movie movie) {
        mMovieDao.favoriteMovie(movie.getId());
    }
}
