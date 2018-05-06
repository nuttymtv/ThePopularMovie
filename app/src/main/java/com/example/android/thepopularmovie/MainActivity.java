package com.example.android.thepopularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
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

import com.example.android.thepopularmovie.Service.MovieApiService;
import com.example.android.thepopularmovie.Service.MovieListAsyncTask;
import com.example.android.thepopularmovie.Utils.JsonUtils;
import com.example.android.thepopularmovie.interfaces.CallBack;
import com.example.android.thepopularmovie.model.MovieModel;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListAdapterOnClickHandler {

    @BindView(R.id.recycler_view_movie_list)
    RecyclerView mMovieListView;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    private MovieListAdapter mMovieListAdapter;
    private List<MovieModel> moviesList;
    private MovieListAsyncTask fetchMovieListTask;
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


        mMovieListView.setLayoutManager(new GridLayoutManager(this, numberOfCol));
        mMovieListAdapter = new MovieListAdapter(this);

        mMovieListView.setAdapter(mMovieListAdapter);
        loadMovieList(SORT_ORDER_MOST_POPULAR);
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
        Context context = MainActivity.this;
        if (selectedMenuItem == R.id.sort_by_popular){
            String msg = "Sort by popular";
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            loadMovieList(SORT_ORDER_MOST_POPULAR);

        } else if (selectedMenuItem == R.id.sort_by_top_rated){
            String msg = "Sort by Top Rated";
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            loadMovieList(SORT_ORDER_TOP_RATED);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovieList(String sortOrder){
        showLoadingIndicator();

        fetchMovieListTask = new MovieListAsyncTask(new CallBack() {
            @Override
            public void onSuccess(Object obj) {
                hideLoadingIndicator();
                moviesList = (List<MovieModel>) obj;
                mMovieListAdapter.setMovieData(moviesList);
            }

            @Override
            public void onFailer(Exception e) {
                hideLoadingIndicator();
                showErrorMessage();
            }
        });
        fetchMovieListTask.execute(sortOrder);
    }

    @Override
    public void onMovieListItemClick(MovieModel movie) {
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