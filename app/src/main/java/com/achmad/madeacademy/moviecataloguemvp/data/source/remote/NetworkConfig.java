package com.achmad.madeacademy.moviecataloguemvp.data.source.remote;

import com.achmad.madeacademy.moviecataloguemvp.utils.Constans;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.BASE_URL;

public class NetworkConfig {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
//    public ApiService api(){
//        return getNetwork().create(ApiService.class);
//    }


}
