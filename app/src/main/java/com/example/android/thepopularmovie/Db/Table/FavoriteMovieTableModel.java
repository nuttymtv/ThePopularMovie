package com.example.android.thepopularmovie.Db.Table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.thepopularmovie.Models.MovieModel.Movie;

import java.util.List;

@Entity
public class FavoriteMovieTableModel {
    public FavoriteMovieTableModel(Movie movie) {

        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
        this.movie_id = movie.getId();
        this.title = movie.getTitle();
        this.popularity = movie.getPopularity();
        this.posterPath = movie.getPosterPath();
        this.originalLanguage = movie.getOriginalLanguage();
        this.originalTitle = movie.getOriginalTitle();
        this.genreIds = movie.getGenreIds();
        this.backdropPath = movie.getBackdropPath();
        this.adult = movie.getAdult();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
    }

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)

    Long id;
    @ColumnInfo
    Integer voteCount;
    @ColumnInfo
    Float voteAverage;
    @ColumnInfo
    Integer movie_id;
    @ColumnInfo
    String title;
    @ColumnInfo
    Double popularity;
    @ColumnInfo
    String posterPath;
    @ColumnInfo
    String originalLanguage;
    @ColumnInfo
    String originalTitle;
    @ColumnInfo
    List<Integer> genreIds = null;
    @ColumnInfo
    String backdropPath;
    @ColumnInfo
    Boolean adult;
    @ColumnInfo
    String overview;
    @ColumnInfo
    String releaseDate;
}
