package com.ajdi.yassin.popularmovies.data.local.dao;

import com.ajdi.yassin.popularmovies.data.local.model.Cast;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
@Dao
public interface CastsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllCasts(List<Cast> castList);

}
