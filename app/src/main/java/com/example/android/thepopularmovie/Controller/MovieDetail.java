package com.example.android.thepopularmovie.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.thepopularmovie.Db.MovieDatabase;
import com.example.android.thepopularmovie.Db.Table.FavoriteMovieTableModel;
import com.example.android.thepopularmovie.Models.MovieModel.Movie;
import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailer;
import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailerResponse;
import com.example.android.thepopularmovie.MovieTrailerListAdapter;
import com.example.android.thepopularmovie.R;
import com.example.android.thepopularmovie.Service.MovieApiServiceInterface;
import com.example.android.thepopularmovie.Utils.ApiUtils;
import com.squareup.picasso.Picasso;


import java.lang.ref.WeakReference;

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
    @BindView(R.id.toggle_button_add_fav_movie)
    ToggleButton tbAddFavMovie;

    private Movie movie;
    private MovieApiServiceInterface mMovieService;
    private MovieTrailerListAdapter movieTrailerListAdapter;
    private FavoriteMovieTableModel mFavMovieTableModel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent parentIntent = getIntent();
        ButterKnife.bind(this);
        context = getApplicationContext();
        mMovieService = ApiUtils.getApiClient();
        movieTrailerListAdapter = new MovieTrailerListAdapter(this);
        rvMovieTrailer.setAdapter(movieTrailerListAdapter);
        rvMovieTrailer.setLayoutManager(new LinearLayoutManager(this));
        if (parentIntent.hasExtra(PARENT_INTENT_PARCEL_NAME)){
            movie = parentIntent.getParcelableExtra(PARENT_INTENT_PARCEL_NAME);
            mFavMovieTableModel  = new FavoriteMovieTableModel(movie);
            checkFavoriteMovieState();
            populateUI();
        }

    }

    private CompoundButton.OnCheckedChangeListener setListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tbAddFavMovie.setBackgroundColor(getResources().getColor(R.color.deep_purple));
                    addFavoriteMovieToDatabase(mFavMovieTableModel,context);

                }else{
                    tbAddFavMovie.setBackgroundColor(getResources().getColor(R.color.amber));
                    removeFavoriteMovie(mFavMovieTableModel,context);
                }
            }
        };
    }

    private void populateUI () {
        String posterPath = ApiUtils.getImgUrlWithSizeProvided(movie.getPosterPath(),"780");
        String releaseDate = movie.getReleaseDate();
        String rating = movie.getVoteAverage() + " / 10";
        setPosterImage(posterPath);
        loadMovieTrailer(movie.getId());
        mMovieTitle.setText(movie.getTitle());
        mMovieReleaseDate.setText(releaseDate);
        mMovieRating.setText(rating);
        mMovieOverview.setText(movie.getOverview());
        setToggleButtonStyle();
    }


    private void setToggleButtonStyle(){
        tbAddFavMovie.setTextOn("Remove favorite");
        tbAddFavMovie.setTextOff("Add to Favorite");
        tbAddFavMovie.setTextColor(getResources().getColor(R.color.white));
        tbAddFavMovie.setChecked(false);
    }


    private static void addFavoriteMovieToDatabase(FavoriteMovieTableModel mFavMovieTableModel, final Context context){
        final MovieDatabase movieDatabase  = MovieDatabase.getInstance(context);
        new AsyncTask<FavoriteMovieTableModel, Void, Void>() {
            @Override
            protected Void doInBackground(FavoriteMovieTableModel... favoriteMovieTableModels) {
                movieDatabase.favoriteMovieDao().insetFavoriteMovie(favoriteMovieTableModels[0]);
                return null;
            }
        }.execute(mFavMovieTableModel);
    }

    private static void removeFavoriteMovie(FavoriteMovieTableModel favoriteMovieTableModel, final Context context){
        final MovieDatabase movieDatabase  = MovieDatabase.getInstance(context);
        new AsyncTask<FavoriteMovieTableModel, Void, Void>() {
            @Override
            protected Void doInBackground(FavoriteMovieTableModel... favoriteMovieTableModels) {
                movieDatabase.favoriteMovieDao().deleteFavoriteMovie(favoriteMovieTableModels[0].movie_id);
                return null;
            }
        }.execute(favoriteMovieTableModel);
    }

    private static class CheckFavoriteMovieState extends AsyncTask<FavoriteMovieTableModel, Void, FavoriteMovieTableModel>{
        private MovieDatabase movieDatabase;
        private WeakReference<MovieDetail> activityRef;

        CheckFavoriteMovieState (MovieDetail context){
            activityRef = new WeakReference<>(context);
            movieDatabase = MovieDatabase.getInstance(activityRef.get());
        }

        @Override
        protected FavoriteMovieTableModel doInBackground(FavoriteMovieTableModel... favoriteMovieTableModels) {
            return movieDatabase.favoriteMovieDao().getFavMovie(favoriteMovieTableModels[0].movie_id);
        }

        @Override
        protected void onPostExecute(FavoriteMovieTableModel favoriteMovieTableModel) {
            super.onPostExecute(favoriteMovieTableModel);
            MovieDetail activity = activityRef.get();
            if (favoriteMovieTableModel != null){
                activity.tbAddFavMovie.setChecked(true);
                activity.tbAddFavMovie.setBackgroundColor(activity.getResources().getColor(R.color.deep_purple));
            }
            activity.tbAddFavMovie.setOnCheckedChangeListener(activity.setListener());
        }
    }


    private void checkFavoriteMovieState() {
        FavoriteMovieTableModel favoriteMovieTableModel = new FavoriteMovieTableModel(movie);
        new CheckFavoriteMovieState(this).execute(favoriteMovieTableModel);
    }



    private void loadMovieTrailer (int id) {
        mMovieService.getMovieTrailer(Integer.toString(id)).enqueue(
                new Callback<MovieTrailerResponse>() {
                    @Override
                    public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                        if (response.isSuccessful()){
                            movieTrailerListAdapter.setData(response.body().getMovieTrailers());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {

                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tbAddFavMovie.setOnCheckedChangeListener(null);
    }

    private void setPosterImage(String imageUrl){
        Picasso.get()
                .load(imageUrl)
                .into(mMoviePoster);
    }



    @Override
    public void onTrailerListItemClick(MovieTrailer trailer) {
        //launch Youtube app(if installed) otherwise launch youtube website
        launchYoutubeOrWebpage(trailer);
    }

    private void launchYoutubeOrWebpage(MovieTrailer movieTrailer) {
        Uri uri = ApiUtils.getYoutubeUriWithKey(movieTrailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
