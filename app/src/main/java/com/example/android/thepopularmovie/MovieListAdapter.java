package com.example.android.thepopularmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.thepopularmovie.Models.MovieModel.Movie;
import com.example.android.thepopularmovie.Utils.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListAdapterViewHolder> {

    private List<Movie> mMovieDataList;
    private final MovieListAdapterOnClickHandler mItemClickHandler;

    public MovieListAdapter(MovieListAdapterOnClickHandler clickHandler){
        this.mItemClickHandler = clickHandler;
    }


    public interface MovieListAdapterOnClickHandler{
        void onMovieListItemClick (Movie movie);
    }

    @NonNull
    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieListItemView = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieListAdapterViewHolder(movieListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapterViewHolder holder, int position) {
        Movie movie = mMovieDataList.get(position);
        String posterPath = movie.getPosterPath();
        String imgUrlString = ApiUtils.getImgUrlWithSizeProvided(posterPath,"500");

        Picasso.get()
                .load(imgUrlString)
                .into(holder.imageViewMoviePoster);

    }

    @Override
    public int getItemCount() {
        if (mMovieDataList == null) return 0;
        return mMovieDataList.size();
    }


    public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_movie_poster)
        ImageView imageViewMoviePoster;


        public MovieListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemClickedIndex = getAdapterPosition();
            Movie movie = mMovieDataList.get(itemClickedIndex);
            mItemClickHandler.onMovieListItemClick(movie);

        }
    }

    public void setMovieData(List<Movie> movieData, int page){
        if (page == 1) this.mMovieDataList = movieData;
        else if (mMovieDataList.size() != 0) mMovieDataList.addAll(movieData);
        notifyDataSetChanged();
    }

}
