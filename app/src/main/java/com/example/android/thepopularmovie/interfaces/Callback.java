package com.example.android.thepopularmovie.interfaces;

public interface Callback<T> {
    void onSuccess(T obj);
    void onFailer(Exception e);
}
