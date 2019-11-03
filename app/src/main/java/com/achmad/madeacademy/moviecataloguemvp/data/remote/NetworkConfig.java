package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Const.BASE_URL;

public class NetworkConfig {
    private static final OkHttpClient client;
    private static ApiService INSTANCE;
    private static final Object lockObject = new Object();

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BASIC);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new NetworkInterceptor())
                .build();
    }

    static ApiService getInstance() {
        synchronized (lockObject) {
            if (INSTANCE == null) {
                INSTANCE = getRetrofitInstance().create(ApiService.class);
            }
            return INSTANCE;
        }
    }
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
