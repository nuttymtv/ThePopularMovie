package com.example.android.thepopularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MovieModel implements Parcelable{

    private String image;
    private String backdropPath;
    private String title;
    private String overview;
    private String releaseDate;
    private int id;
    private int voteCount;
    private int avgVote;
    private int popularity;
    private boolean hasVideo;
    private boolean isRatedAdult;


    /**
     * @param image : path for poster image
     * @param backdropPath path for backdrop image
     * @param title Title of the movie
     * @param overview brief description of the movie
     * @param releaseDate Date released
     * @param id ID
     * @param voteCount Vote count
     * @param avgVote Average Vote
     * @param popularity Popularity
     * @param hasVideo Has video attached or not
     * @param isRatedAdult Is this movie adult rated
     */
    public MovieModel(String image ,
                      String backdropPath ,
                      String title,
                      String overview,
                      String releaseDate,
                      int id,
                      int voteCount,
                      int avgVote,
                      int popularity,
                      boolean hasVideo,
                      boolean isRatedAdult) {

        this.image = image;
        this.backdropPath = backdropPath;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.voteCount = voteCount;
        this.avgVote = avgVote;
        this.popularity = popularity;
        this.hasVideo = hasVideo;
        this.isRatedAdult = isRatedAdult;
    }

    protected MovieModel(Parcel in) {
        image = in.readString();
        backdropPath = in.readString();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();
        voteCount = in.readInt();
        avgVote = in.readInt();
        popularity = in.readInt();
        hasVideo = in.readByte() != 0;
        isRatedAdult = in.readByte() != 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getAvgVote() {
        return avgVote;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public boolean isRatedAdult() {
        return isRatedAdult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(backdropPath);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeInt(voteCount);
        dest.writeInt(avgVote);
        dest.writeInt(popularity);
        dest.writeByte((byte) (hasVideo ? 1 : 0));
        dest.writeByte((byte) (isRatedAdult ? 1 : 0));
    }
}
