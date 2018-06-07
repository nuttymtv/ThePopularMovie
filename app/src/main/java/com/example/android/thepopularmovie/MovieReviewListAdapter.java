package com.example.android.thepopularmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.thepopularmovie.Models.MovieReviewModel.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewListAdapter.MovieReviewListViewHolder> {
    private List<MovieReview> movieReviews;

    @NonNull
    @Override
    public MovieReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.movie_review_list_item;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View reviewListItemView = inflater.inflate(layoutId,parent,false);
        return new MovieReviewListViewHolder(reviewListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewListViewHolder holder, int position) {
        MovieReview movieReview = movieReviews.get(position);

        holder.tvAuthorComment.setText(movieReview.getContent());
        holder.tvAuthorName.setText(movieReview.getAuthor());
    }


    @Override
    public int getItemCount() {
        if (movieReviews == null) return 0;
        return movieReviews.size();
    }

    public void setData(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
        notifyDataSetChanged();
    }


    /*
    * Custom View Holder
    */
    public class MovieReviewListViewHolder
        extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_author_comment)
        TextView tvAuthorComment;

        public MovieReviewListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
