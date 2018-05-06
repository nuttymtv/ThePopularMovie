package com.example.android.thepopularmovie.Service;

import android.os.AsyncTask;

import com.example.android.thepopularmovie.Utils.JsonUtils;
import com.example.android.thepopularmovie.interfaces.CallBack;
import com.example.android.thepopularmovie.model.MovieModel;

import java.net.URL;
import java.util.List;

public class MovieListAsyncTask extends AsyncTask<String , Void, List<MovieModel>>{

    private CallBack<List<MovieModel>> mCallback;
    private Exception mException;

    public MovieListAsyncTask(CallBack mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(String... strings) {
        String sortOrder = strings[0];
        URL movieUrl = MovieApiService.buildUrl(sortOrder);

        try {
            String jsonRes = MovieApiService.getResponseFromHttpUrl(movieUrl);
            return JsonUtils.parseDataToList(jsonRes);
        }catch (Exception e){
            mException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieList) {
        if (movieList != null){
            mCallback.onSuccess(movieList);
        }else{
            mCallback.onFailer(mException);
        }
    }
}
