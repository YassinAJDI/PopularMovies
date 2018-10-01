package com.ajdi.yassin.popularmoviespart1.utils;

import com.ajdi.yassin.popularmoviespart1.data.MovieRepository;
import com.ajdi.yassin.popularmoviespart1.data.api.ApiClient;

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
        return new MovieRepository(ApiClient.getInstance(), AppExecutors.getInstance());
    }
}
