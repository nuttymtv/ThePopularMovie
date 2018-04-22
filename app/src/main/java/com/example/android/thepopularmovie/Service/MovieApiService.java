package com.example.android.thepopularmovie.Service;

import android.net.Uri;
import android.text.StaticLayout;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MovieApiService {

    private static final String MOVIE_API_ENDPOINT =
            "https://api.themoviedb.org/3/movie";
    private static final String POPULAR_LIST = "popular";
    private static final String TOP_RATED_LIST = "top_rated";
    private static final String API_KEY_PARAM = "api_key";
    private static final String key = "7f8a4a1b19febf2b4c14e0e9881b8a35";
    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/";


    public static URL buildUrl(String sortOrder) {
        String sort = sortOrder == POPULAR_LIST ? POPULAR_LIST : TOP_RATED_LIST;
        Uri builtUri = Uri.parse(MOVIE_API_ENDPOINT).buildUpon()
                .appendPath(sort)
                .appendQueryParameter(API_KEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("MOVIE URL", "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
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
}
