package com.example.android.thepopularmovie.Controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thepopularmovie.Models.MovieModel.Movie;
import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailer;
import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailerResponse;
import com.example.android.thepopularmovie.MovieTrailerListAdapter;
import com.example.android.thepopularmovie.R;
import com.example.android.thepopularmovie.Service.MovieApiServiceInterface;
import com.example.android.thepopularmovie.Utils.ApiUtils;
import com.squareup.picasso.Picasso;



import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity implements MovieTrailerListAdapter.TrailerListAdapterOnClickHandler{

    private static final String PARENT_INTENT_PARCEL_NAME = "movie_object";
    @BindView(R.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.iv_movie_poster_detail)
    ImageView mMoviePoster;
    @BindView(R.id.tv_movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.tv_movie_rating)
    TextView mMovieRating;
    @BindView(R.id.tv_movie_overview)
    TextView mMovieOverview;
    @BindView(R.id.recycler_view_movie_trailer)
    RecyclerView rvMovieTrailer;

    private Movie movie;
    private MovieApiServiceInterface mMovieService;
    private MovieTrailerListAdapter movieTrailerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent parentIntent = getIntent();
        ButterKnife.bind(this);

        mMovieService = ApiUtils.getApiClient();
        movieTrailerListAdapter = new MovieTrailerListAdapter(this);

        rvMovieTrailer.setAdapter(movieTrailerListAdapter);
        rvMovieTrailer.setLayoutManager(new LinearLayoutManager(this));

        if (parentIntent.hasExtra(PARENT_INTENT_PARCEL_NAME)){
            movie = parentIntent.getParcelableExtra(PARENT_INTENT_PARCEL_NAME);
            populateUI();
        }
    }

    private void populateUI () {
        String posterPath = ApiUtils.getImgUrlWithSizeProvided(movie.getPosterPath(),"185");
        String releaseDate = movie.getReleaseDate();
        String rating = movie.getVoteAverage() + " / 10";
        setPosterImage(posterPath);
        loadMovieTrailer(movie.getId());
        mMovieTitle.setText(movie.getTitle());
        mMovieReleaseDate.setText(releaseDate);
        mMovieRating.setText(rating);
        mMovieOverview.setText(movie.getOverview());

    }

    private void loadMovieTrailer (int id) {
        mMovieService.getMovieTrailer(Integer.toString(id)).enqueue(
                new Callback<MovieTrailerResponse>() {
                    @Override
                    public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                        if (response.isSuccessful()){
                            // TODO: Populate custom list adaptor
                            movieTrailerListAdapter.setData(response.body().getMovieTrailers());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {

                    }
                }
        );
    }

    private void setPosterImage(String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .into(mMoviePoster);
    }

    @Override
    public void onTrailerListItemClick(MovieTrailer trailer) {
        MovieTrailer movieTrailer = trailer;

        //launch Youtube app(if installed) otherwise launch youtube website
        launchYoutubeOrWebpage(movieTrailer);
    }

    private void launchYoutubeOrWebpage(MovieTrailer movieTrailer) {
        Uri uri = ApiUtils.getYoutubeUriWithKey(movieTrailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
