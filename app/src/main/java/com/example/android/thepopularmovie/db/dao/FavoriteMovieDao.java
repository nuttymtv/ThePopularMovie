package com.example.android.thepopularmovie.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.thepopularmovie.db.table.FavoriteMovieTableModel;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM FavoriteMovieTableModel")
    LiveData<List<FavoriteMovieTableModel>> getFavoriteMovieList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetFavoriteMovie(FavoriteMovieTableModel favoriteMovie);

    @Query("DELETE FROM FavoriteMovieTableModel")
    void deleteAll ();

    @Query("SELECT * FROM FavoriteMovieTableModel WHERE movie_id LIKE :id")
    FavoriteMovieTableModel getFavMovie(int id);

    @Query("DELETE FROM FavoriteMovieTableModel WHERE movie_id LIKE :id")
    void deleteFavoriteMovie(int id);
}
