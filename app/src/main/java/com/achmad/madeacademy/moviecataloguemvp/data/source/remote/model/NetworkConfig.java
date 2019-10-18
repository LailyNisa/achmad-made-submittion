package com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Cons.BASE_URL;

public class NetworkConfig {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
