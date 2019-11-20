package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;

import java.util.ArrayList;

public interface LoadMovieCallBack {
    void preExecute();
    void postExecute(ArrayList<Result> movie);
}
