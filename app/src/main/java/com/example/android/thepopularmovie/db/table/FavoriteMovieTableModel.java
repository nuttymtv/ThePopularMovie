package com.example.android.thepopularmovie.db.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.thepopularmovie.models.movie.Movie;

@Entity
public class FavoriteMovieTableModel {

    @Ignore
    public FavoriteMovieTableModel(Movie movie) {
        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
        this.movie_id = movie.getId();
        this.title = movie.getTitle();
        this.popularity = movie.getPopularity();
        this.posterPath = movie.getPosterPath();
        this.originalLanguage = movie.getOriginalLanguage();
        this.originalTitle = movie.getOriginalTitle();
        this.backdropPath = movie.getBackdropPath();
        this.adult = movie.getAdult();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
    }

    public FavoriteMovieTableModel(){}

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)

    public Long id;
    @ColumnInfo
    public Integer voteCount;
    @ColumnInfo
    public Float voteAverage;
    @ColumnInfo
    public Integer movie_id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public Double popularity;
    @ColumnInfo
    public String posterPath;
    @ColumnInfo
    public String originalLanguage;
    @ColumnInfo
    public String originalTitle;
    @ColumnInfo
    public String backdropPath;
    @ColumnInfo
    public Boolean adult;
    @ColumnInfo
    public String overview;
    @ColumnInfo
    public String releaseDate;
}
