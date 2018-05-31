package com.example.android.thepopularmovie.Service;


import com.example.android.thepopularmovie.Models.MovieModel.MovieResponse;
import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailerResponse;

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
}
