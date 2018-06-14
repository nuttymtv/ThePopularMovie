package com.example.android.thepopularmovie.service;


import com.example.android.thepopularmovie.models.movie.MovieResponse;
import com.example.android.thepopularmovie.models.movie_review.MovieReviewResponse;
import com.example.android.thepopularmovie.models.movie_trailer.MovieTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiServiceInterface {

    /*
    * Get a list of movies with detail contained
    */
    @GET("{sort}")
    Call<MovieResponse> getMovies(@Path("sort") String sort,@Query("page") int page);

    @GET("{id}/videos")
    Call<MovieTrailerResponse> getMovieTrailer(@Path("id") String movieId);

    @GET("{id}/reviews")
    Call<MovieReviewResponse> getMovieReview(@Path("id") String movieId);
}
