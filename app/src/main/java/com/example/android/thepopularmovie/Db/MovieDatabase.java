package com.example.android.thepopularmovie.Db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.thepopularmovie.Db.Dao.FavoriteMovieDao;
import com.example.android.thepopularmovie.Db.Table.FavoriteMovieTableModel;

@Database(entities = {FavoriteMovieTableModel.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase INSTANCE;
    public abstract FavoriteMovieDao favoriteMovieDao();
}
