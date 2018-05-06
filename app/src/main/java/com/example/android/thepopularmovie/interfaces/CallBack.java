package com.example.android.thepopularmovie.interfaces;

/**
 * Callback interface used to send back data to th caller
 */
public interface CallBack<T> {
    void onSuccess(T obj);
    void onFailer(Exception e);
}
