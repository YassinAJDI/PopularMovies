package com.ajdi.yassin.popularmovies.data.local.dao;

import com.ajdi.yassin.popularmovies.data.local.model.Review;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

/**
 * @author Yassin Ajdi
 * @since 11/13/2018.
 */
@Dao
public interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllReviews(List<Review> reviews);

}
