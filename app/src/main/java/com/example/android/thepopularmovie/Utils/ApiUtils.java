package com.example.android.thepopularmovie.Utils;

import android.net.Uri;

import com.example.android.thepopularmovie.Service.Client.ApiClient;
import com.example.android.thepopularmovie.Service.MovieApiServiceInterface;

import java.net.URL;

import retrofit2.Retrofit;

public class ApiUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";


    public static MovieApiServiceInterface getApiClient(){
        return ApiClient.getClient(BASE_URL).create(MovieApiServiceInterface.class);
    }


    public static String getImgUrlWithSizeProvided(String posterPath, String size){
        String imgSize;

        switch (size){
            case "92" : imgSize = "w92";break;
            case "154" : imgSize = "w154";break;
            case "185" : imgSize = "w185";break;
            case "342" : imgSize = "w342";break;
            case "500" : imgSize = "w500";break;
            case "780" : imgSize = "w780";break;
            case "original" : imgSize = "original";break;
            default: imgSize = "w185";
        }

        Uri imgUri = Uri.parse(IMG_BASE_URL).buildUpon()
                .appendPath(imgSize)
                .build();

        return imgUri.toString()+posterPath;
    }

    public static Uri getYoutubeUriWithKey(String key){
        Uri uri = Uri.parse(YOUTUBE_BASE_URL + key);
        return uri;
    }
}
