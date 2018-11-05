package com.ajdi.yassin.popularmoviespart1.utils;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
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
        return MovieRepository.getInstance(ApiClient.getInstance(), AppExecutors.getInstance());
    }
}
