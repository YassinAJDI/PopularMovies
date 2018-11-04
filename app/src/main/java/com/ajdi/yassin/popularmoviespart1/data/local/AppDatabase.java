package com.ajdi.yassin.popularmoviespart1.data.local;

import android.content.Context;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The Room Database that manages a local database.
 *
 * @author Yassin Ajdi.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Movies.db";

    public abstract MoviesDao moviesDao();

    private static AppDatabase INSTANCE;

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).build();
    }
}
