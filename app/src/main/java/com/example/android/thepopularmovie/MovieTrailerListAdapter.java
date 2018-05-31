package com.example.android.thepopularmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.thepopularmovie.Models.MovieTrailerModel.MovieTrailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailerListAdapter extends
        RecyclerView.Adapter<MovieTrailerListAdapter.MovieTrailerListViewHolder> {

    private List<MovieTrailer> movieTrailers;
    private final  TrailerListAdapterOnClickHandler mItemClickHandler;

    public interface TrailerListAdapterOnClickHandler {
        void onTrailerListItemClick (MovieTrailer trailer);
    }

    public MovieTrailerListAdapter (TrailerListAdapterOnClickHandler handler){
        this.mItemClickHandler = handler;
    }


    @NonNull
    @Override
    public MovieTrailerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int itemLayoutId = R.layout.movie_trailer_list_item;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View trailerListItemView = inflater.inflate(itemLayoutId,parent,false);

        return new MovieTrailerListViewHolder(trailerListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerListViewHolder holder, int position) {
        MovieTrailer trailer = movieTrailers.get(position);
        String videoTitle = trailer.getName();
        String videoSite = trailer.getSite();
        String videoType = trailer.getType();

        holder.tvTrailerName.setText(videoTitle);
        holder.tvTrailerSite.setText(videoSite);
        holder.tvTrailerVideoType.setText(videoType);

    }

    @Override
    public int getItemCount() {
        if (movieTrailers == null) return 0;
        return movieTrailers.size();
    }

    public void setData(List<MovieTrailer> trailers){
        this.movieTrailers = trailers;
        notifyDataSetChanged();
    }


    /*
    * Custom ViewHolder Class
    */
    public class MovieTrailerListViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_trailer_name)
        TextView tvTrailerName;
        @BindView(R.id.tv_trailer_site)
        TextView tvTrailerSite;
        @BindView(R.id.tv_video_type)
        TextView tvTrailerVideoType;

        public MovieTrailerListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            MovieTrailer trailer = movieTrailers.get(index);
            mItemClickHandler.onTrailerListItemClick(trailer);
        }
    }
}
