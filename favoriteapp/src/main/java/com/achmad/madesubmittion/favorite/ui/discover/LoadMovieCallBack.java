package com.achmad.madesubmittion.favorite.ui.discover;

import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;

import java.util.ArrayList;

public interface LoadMovieCallBack {
    void preExecute();

    void postExecute(ArrayList<Result> movie);
}
