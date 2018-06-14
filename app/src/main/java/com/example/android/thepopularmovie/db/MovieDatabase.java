package com.example.android.thepopularmovie.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.thepopularmovie.db.dao.FavoriteMovieDao;
import com.example.android.thepopularmovie.db.table.FavoriteMovieTableModel;


@Database(entities = {FavoriteMovieTableModel.class},version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase INSTANCE;
    private static  final Object LOCK = new Object();
    public static final String DB_NAME = "movie_database";

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static MovieDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (LOCK){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class , MovieDatabase.DB_NAME)
                .build();
            }
        }
        return INSTANCE;
    }

    public static void  destroyInstance (){
        INSTANCE = null;
    }


}
