package com.achmad.madeacademy.moviecataloguemvp.data.source.remote;

import com.achmad.madeacademy.moviecataloguemvp.utils.Constans;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.BASE_URL;

public class NetworkConfig {
    private OkHttpClient okHttpClient;
    private static ApiService mInstance;

    private OkHttpClient getInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient.newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return okHttpClient;
    }

    public Retrofit getNetwork(){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService api(){
        return getNetwork().create(ApiService.class);
    }


}
