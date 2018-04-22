package com.example.android.thepopularmovie;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thepopularmovie.Service.MovieApiService;
import com.example.android.thepopularmovie.Utils.JsonUtils;
import com.example.android.thepopularmovie.model.MovieModel;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_movie_list)
    RecyclerView mMovieListView;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    private MovieListAdapter mMovieListAdapter;
    private List<MovieModel> moviesList;
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
        mMovieListAdapter = new MovieListAdapter();

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
        new FetchMovieListTask().execute(sortOrder);
    }


    public class FetchMovieListTask extends AsyncTask<String,Void,List<MovieModel>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MovieModel> doInBackground(String... strings) {
            String sortOrder = strings[0];
            URL movieUrl = MovieApiService.buildUrl(sortOrder);

            try {
                String jsonRes = MovieApiService.getResponseFromHttpUrl(movieUrl);
                moviesList = JsonUtils.parseDataToList(jsonRes);
                return moviesList;
            }catch (Exception e){
                e.printStackTrace();
                return  null;
            }
        }

        @Override
        protected void onPostExecute(List<MovieModel> movieList) {
            if (movieList != null){
                mMovieListAdapter.setMovieData(movieList);
            }
        }
    }
}