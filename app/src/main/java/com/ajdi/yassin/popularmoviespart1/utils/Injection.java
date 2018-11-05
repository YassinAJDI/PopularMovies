package com.ajdi.yassin.popularmoviespart1.utils;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.remote.MoviesRemoteDataSource;
import com.ajdi.yassin.popularmoviespart1.data.remote.api.ApiClient;

/**
 * Class that handles object creation.
 *
 * @author Yassin Ajdi.
 */
public class Injection {

    /**
     * Creates an instance of MovieRepository
     */
    public static MovieRepository provideMovieRepository() {
        return MovieRepository.getInstance(provideMoviesRemoteDataSource(), AppExecutors.getInstance());
    }

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    public static MoviesRemoteDataSource provideMoviesRemoteDataSource() {
        return MoviesRemoteDataSource.getInstance(ApiClient.getInstance(), AppExecutors.getInstance());
    }
}
