package com.example.android.thepopularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thepopularmovie.Service.MovieApiService;
import com.example.android.thepopularmovie.model.MovieModel;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {

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

    private MovieModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent parentIntent = getIntent();
        ButterKnife.bind(this);
        if (parentIntent.hasExtra(PARENT_INTENT_PARCEL_NAME)){
            movie = parentIntent.getParcelableExtra(PARENT_INTENT_PARCEL_NAME);
            populateUI();
        }
    }

    private void populateUI () {
        String posterPath = MovieApiService.getImgUrlWithSizeProvided(movie.getImage(),"185");
        String releaseDate = movie.getReleaseDate();
        String rating = movie.getAvgVote() + " / 10";
        setPosterImage(posterPath);

        mMovieTitle.setText(movie.getTitle());
        mMovieReleaseDate.setText(releaseDate);
        mMovieRating.setText(rating);
        mMovieOverview.setText(movie.getOverview());

    }

    private void setPosterImage(String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .into(mMoviePoster);
    }
}
