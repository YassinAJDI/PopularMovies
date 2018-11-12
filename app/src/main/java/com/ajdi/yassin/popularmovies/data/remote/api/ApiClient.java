package com.ajdi.yassin.popularmovies.data.remote.api;

import com.ajdi.yassin.popularmovies.utils.LiveDataCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yassin Ajdi.
 */
public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final OkHttpClient client;

    private static MovieService sInstance;

    private static final Object sLock = new Object();

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    public static MovieService getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = getRetrofitInstance().create(MovieService.class);
            }
            return sInstance;
        }
    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(client)
                .build();
    }
}
