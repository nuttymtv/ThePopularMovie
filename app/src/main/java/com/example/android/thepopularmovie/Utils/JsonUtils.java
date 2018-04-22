package com.example.android.thepopularmovie.Utils;

import android.graphics.Movie;

import com.example.android.thepopularmovie.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    private static final String RESULTS = "results";

    public static List<MovieModel> parseDataToList(String jsonString) throws JSONException{
        List<MovieModel> parsedResult = new ArrayList<>();


        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray extractedDataArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < extractedDataArray.length() ; i++) {
            JSONObject item = extractedDataArray.getJSONObject(i);
            String imgPath = item.getString("poster_path");
            parsedResult.add(new MovieModel(imgPath));
        }
        return parsedResult;
    }
}
