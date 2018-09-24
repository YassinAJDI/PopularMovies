package com.ajdi.yassin.popularmoviespart1.data.remote.api;

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

    private static MovieApiService INSTANCE;

    private static final Object sLock = new Object();

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public static MovieApiService getInstance() {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = getRetrofitInstance().create(MovieApiService.class);
            }
            return INSTANCE;
        }
    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
