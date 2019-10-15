package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.source.remote.model.Movie;

import static com.achmad.madeacademy.moviecataloguemvp.utils.Constans.API_KEY;

public class DiscoverViewModel extends ViewModel {

//    public static MutableLiveData<Movie> data;
//    public static MutableLiveData<Boolean> status;
//    public static Call<Movie> call;
    private MutableLiveData<Movie> mutableLiveData;
    private final MutableLiveData<Integer> language = new MutableLiveData<>();

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        language.setValue(R.string.setting_language);
        NetworkRepository networkRepository = NetworkRepository.getInstance();
        mutableLiveData = networkRepository.getMovie(API_KEY, "fr-FR");
    }

    LiveData<Movie> getMovieRepository(){
        return mutableLiveData;
    }
//
//    static {
//        getData();
//    }
//
//    public DiscoverViewModel() {
//    }
//
//    public static void getData() {
//
//        status.postValue(true);
//        call = new NetworkConfig().api().getMovie(API_KEY,LANGUAGE);
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                status.setValue(false);
//                if (response.isSuccessful()){
//                    data.postValue(response.body());
//                }else {
//                    status.postValue(true);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                status.postValue(false);
//                data.postValue(null);
//                Log.d("onFailur()...",t.getMessage());
//            }
//        });
////        new NetworkConfig().api().getMovie(API_KEY, LANGUAGE).enqueue(new Callback<Movie>() {
////            @Override
////            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
////                status.setValue(false);
////                if (response.isSuccessful()) {
////                    data.setValue(response.body());
////                } else {
////                    status.setValue(true);
////                }
////            }
////
////            @Override
////            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
////                status.setValue(true);
////                data.setValue(null);
////            }
////        });
//    }
//
//    public static void setData(MutableLiveData<Movie> data) {
//        DiscoverViewModel.data = data;
//    }
//
//    public static void setStatus(MutableLiveData<Boolean> status) {
//        DiscoverViewModel.status = status;
//    }
//
//    public MutableLiveData<Movie> setData() {
//        return data;
//    }
//
//    public MutableLiveData<Boolean> getStatus() {
//        status.setValue(true);
//        return status;
//    }
//

}
