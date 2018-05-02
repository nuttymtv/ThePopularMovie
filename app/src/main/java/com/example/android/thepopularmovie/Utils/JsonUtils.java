package com.example.android.thepopularmovie.Utils;


import com.example.android.thepopularmovie.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class JsonUtils {

    private static final String RESULTS = "results";

    public static List<MovieModel> parseDataToList(String jsonString)
            throws JSONException {

        List<MovieModel> parsedResult = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray extractedDataArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < extractedDataArray.length() ; i++) {
            JSONObject item = extractedDataArray.getJSONObject(i);
            String imgPath = item.getString("poster_path");
            String backDrop = item.getString("backdrop_path");
            String title = item.getString("title");
            String overview = item.getString("overview");
            String releaseDate = item.getString("release_date");

            int id = item.getInt("id");
            int voteCount = item.getInt("vote_count");
            int avgVote = item.getInt("vote_average");
            int popularity = item.getInt("popularity");

            boolean hasVideo = item.getBoolean("video");
            boolean isRatedAdult = item.getBoolean("adult");

            parsedResult.add(new MovieModel(
                    imgPath,
                    backDrop,
                    title,
                    overview,
                    releaseDate,
                    id,
                    voteCount,
                    avgVote,
                    popularity,
                    hasVideo,
                    isRatedAdult)
            );
        }
        return parsedResult;
    }
}
