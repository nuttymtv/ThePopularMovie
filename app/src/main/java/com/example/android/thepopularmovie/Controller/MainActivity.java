package com.example.android.thepopularmovie.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thepopularmovie.EndlessRecyclerViewScrollListener;
import com.example.android.thepopularmovie.Models.MovieModel.Movie;
import com.example.android.thepopularmovie.MovieListAdapter;
import com.example.android.thepopularmovie.R;
import com.example.android.thepopularmovie.Service.MovieApiServiceInterface;
import com.example.android.thepopularmovie.Utils.ApiUtils;
import com.example.android.thepopularmovie.Models.MovieModel.MovieResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListAdapterOnClickHandler {

    @BindView(R.id.recycler_view_movie_list)
    RecyclerView mMovieListView;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    private MovieListAdapter mMovieListAdapter;
    private MovieApiServiceInterface mMovieService;
    private EndlessRecyclerViewScrollListener scrollListener;
    private String currentSorting;
    private static final String SORT_ORDER_TOP_RATED =  "top_rated";
    private static final String SORT_ORDER_MOST_POPULAR =  "popular";


    /**
     * Number of grid column
     */
    static final int numberOfCol = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfCol);
        currentSorting = SORT_ORDER_MOST_POPULAR;
        mMovieService = ApiUtils.getApiClient();

        mMovieListView.setLayoutManager(gridLayoutManager);
        mMovieListAdapter = new MovieListAdapter(this);

        mMovieListView.setAdapter(mMovieListAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMovieList(page,currentSorting);
            }

        };

        mMovieListView.addOnScrollListener(scrollListener);
        loadMovieList(1,currentSorting);

    }

    /**
     *  This is menu option section
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        final int page = 1;
        Context context = MainActivity.this;

        if (selectedMenuItem == R.id.sort_by_popular){
            currentSorting = SORT_ORDER_MOST_POPULAR;
            String msg = "Sort by popular";
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            loadMovieList(page, currentSorting);
        } else if (selectedMenuItem == R.id.sort_by_top_rated){
            currentSorting = SORT_ORDER_TOP_RATED;
            String msg = "Sort by Top Rated";
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            loadMovieList(page, currentSorting);
        }


        return super.onOptionsItemSelected(item);
    }

    private void loadMovieList(final int page, String sortOrder){
        if (page == 1) showLoadingIndicator();

        mMovieService.getMovies(sortOrder,page).enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    mMovieListAdapter.setMovieData(response.body().getMovies() , page);
                    if (page == 1) hideLoadingIndicator();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showErrorMessage();
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    @Override
    public void onMovieListItemClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartMovieDetail = new Intent( context , destinationClass);
        intentToStartMovieDetail.putExtra("movie_object", movie);
        startActivity(intentToStartMovieDetail);
    }

    private void showMovieListData (){
        mMovieListView.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.INVISIBLE);
    }

    private  void showErrorMessage () {
        mMovieListView.setVisibility(View.INVISIBLE);
        mTvError.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator () {
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieListView.setVisibility(View.INVISIBLE);
    }

    private void hideLoadingIndicator () {
        mProgressBar.setVisibility(View.INVISIBLE);
        mMovieListView.setVisibility(View.VISIBLE);
    }
}