package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.NetworkConfig;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.API_KEY;
import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.LANGUAGE;

class DiscoverViewModel extends ViewModel {
    private static MutableLiveData<Movie> data;
    private static MutableLiveData<Boolean> status;

    static {
        getData();
    }

    public DiscoverViewModel() {
    }

    private static void getData() {
        status.setValue(true);
        new NetworkConfig().api().getMovie(API_KEY, LANGUAGE).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                status.setValue(false);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    status.setValue(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
                status.setValue(true);
                data.setValue(null);
            }
        });
    }

    MutableLiveData<Movie> setData() {
        return data;
    }

    MutableLiveData<Boolean> getStatus() {
        status.setValue(true);
        return status;
    }


}
