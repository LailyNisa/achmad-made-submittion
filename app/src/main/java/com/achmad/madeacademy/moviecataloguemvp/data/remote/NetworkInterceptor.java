package com.achmad.madeacademy.moviecataloguemvp.data.remote;

import android.os.Build;
import android.os.LocaleList;

import com.achmad.madeacademy.moviecataloguemvp.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;

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
                .addQueryParameter("language", getSystemLocale())
                .build();

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }

    private String getSystemLocale() {
        String locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0).getLanguage();
        } else {
            locale = Locale.getDefault().getLanguage();
        }
        String localeSet;
        if (locale.equals("fr") ||
                locale.equals("id") ||
                locale.equals("en")) {
            localeSet = locale;
        } else {
            localeSet = "en";
        }
        return localeSet;
    }
}
