package com.ajdi.yassin.popularmovies.data.local.model;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * This class is used to load full movie details including (Trailers, Cast, etc)
 *
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
public class MovieAndTrailers {

    @Embedded
    public Movie movie = null;

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<Trailer> trailers = new ArrayList<>();

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<Cast> castList = new ArrayList<>();
}
