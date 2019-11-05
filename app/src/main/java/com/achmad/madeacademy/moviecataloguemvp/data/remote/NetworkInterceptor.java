package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import com.achmad.madeacademy.moviecataloguemvp.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("with_original_language", "ja")
//                .addQueryParameter("with_genres", "35,10749") -> Comedy,Romance
                .build();

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
