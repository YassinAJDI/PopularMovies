package com.ajdi.yassin.popularmovies.data.local;

import android.content.Context;

import com.ajdi.yassin.popularmovies.data.local.dao.CastsDao;
import com.ajdi.yassin.popularmovies.data.local.dao.MoviesDao;
import com.ajdi.yassin.popularmovies.data.local.dao.ReviewsDao;
import com.ajdi.yassin.popularmovies.data.local.dao.TrailersDao;
import com.ajdi.yassin.popularmovies.data.local.model.Cast;
import com.ajdi.yassin.popularmovies.data.local.model.Converters;
import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.Review;
import com.ajdi.yassin.popularmovies.data.local.model.Trailer;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * The Room Database that manages a local database.
 *
 * @author Yassin Ajdi.
 */
@Database(
        entities = {Movie.class, Trailer.class, Cast.class, Review.class},
        version = 1,
        exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MoviesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Movies.db";

    private static MoviesDatabase INSTANCE;

    private static final Object sLock = new Object();

    public abstract MoviesDao moviesDao();

    public abstract TrailersDao trailersDao();

    public abstract CastsDao castsDao();

    public abstract ReviewsDao reviewsDao();

    public static MoviesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static MoviesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                MoviesDatabase.class,
                DATABASE_NAME).build();
    }
}
