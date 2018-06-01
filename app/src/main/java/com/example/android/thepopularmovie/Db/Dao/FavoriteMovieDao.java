package com.example.android.thepopularmovie.Db.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.thepopularmovie.Db.Table.FavoriteMovieTableModel;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM FavoriteMovieTableModel")
    List<FavoriteMovieTableModel> getFavoriteMovieList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetFavoriteMovie(FavoriteMovieTableModel favoriteMovie);
}
