package com.example.android.thepopularmovie.models.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.example.android.thepopularmovie.db.MovieDatabase;
import com.example.android.thepopularmovie.db.table.FavoriteMovieTableModel;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovieTableModel>> favMovieListLiveData;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        favMovieListLiveData= movieDatabase.favoriteMovieDao().getFavoriteMovieList();
    }

    public LiveData<List<FavoriteMovieTableModel>> getFavMovieListLiveData() {
        return favMovieListLiveData;
    }
}
