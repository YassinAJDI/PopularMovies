package com.ajdi.yassin.popularmovies.data.local.dao;

import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.MovieDetails;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

/**
 * @author Yassin Ajdi.
 */
@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id= :movieId")
    LiveData<MovieDetails> getMovie(long movieId);

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    LiveData<List<Movie>> getAllFavoriteMovies();

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
