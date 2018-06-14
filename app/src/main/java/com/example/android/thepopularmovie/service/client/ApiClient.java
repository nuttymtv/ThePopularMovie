package com.example.android.thepopularmovie.service.client;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String KEY = "7f8a4a1b19febf2b4c14e0e9881b8a35";

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            OkHttpClient okHttpClient =
                    new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request originalRequest = chain.request();
                                    HttpUrl httpUrl = originalRequest.url();

                                    HttpUrl newHttpUrl = httpUrl.newBuilder()
                                            .addQueryParameter("api_key", KEY)
                                            .build();

                                    Request.Builder builder = originalRequest
                                            .newBuilder().url(newHttpUrl);

                                    Request copyRequest = builder.build();

                                    return chain.proceed(copyRequest);
                                }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
