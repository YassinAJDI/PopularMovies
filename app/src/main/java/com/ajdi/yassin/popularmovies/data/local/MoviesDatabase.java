package com.ajdi.yassin.popularmovies.data.local;

import android.content.Context;

import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.Trailer;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The Room Database that manages a local database.
 *
 * @author Yassin Ajdi.
 */
@Database(entities = {Movie.class, Trailer.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Movies.db";

    public abstract MoviesDao moviesDao();

    public abstract TrailersDao trailersDao();

    private static MoviesDatabase INSTANCE;

    private static final Object sLock = new Object();

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
