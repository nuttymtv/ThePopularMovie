package com.example.android.thepopularmovie.models.movie_trailer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> trailers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return trailers;
    }

    public void setResults(List<MovieTrailer> trailers) {
        this.trailers = trailers;
    }

}
