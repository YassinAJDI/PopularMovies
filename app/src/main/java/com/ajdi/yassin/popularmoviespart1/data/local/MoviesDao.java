package com.ajdi.yassin.popularmoviespart1.data.local;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author Yassin Ajdi.
 */
@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    LiveData<List<Movie>> getAllFavoritedMovies();

    /**
     * Favorite a movie.
     *
     * @return the number of movies updated. This should always be 1.
     */
    @Query("UPDATE movie SET is_favorite = 1 WHERE id = :movieId")
    int favoriteMovie(long movieId);

    /**
     * Unfavorite a movie.
     *
     * @return the number of movies updated. This should always be 1.
     */
    @Query("UPDATE movie SET is_favorite = 0 WHERE id = :movieId")
    int unFavoriteMovie(long movieId);

}
