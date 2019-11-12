package com.achmad.selflearning.mydiscoverconsumer.ui.discover;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.selflearning.mydiscoverconsumer.R;
import com.achmad.selflearning.mydiscoverconsumer.data.remote.model.movie.Result;
import com.achmad.selflearning.mydiscoverconsumer.ui.discover.adapter.MovieAdapter;
import com.achmad.selflearning.mydiscoverconsumer.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private MovieAdapter mAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvMovies = (RecyclerView) view;
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
            rvMovies.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        CommonUtils.showLoading(getActivity());
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initDb();
        movieViewModel.getCodeErro().observe(getViewLifecycleOwner(), integer -> {
                    switch (integer) {
                        case 401: {
                            CommonUtils.hideLoading();
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_401), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 404: {
                            CommonUtils.hideLoading();
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_404), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default: {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }

    private void initDb() {
        movieViewModel.initDb();
        movieViewModel.getMovieDb().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                movieResult.addAll(movieResponse);
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof MovieAdapter.OnFragmentInteractionListener) {
            mListener = (MovieAdapter.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setRvMovies() {
        if (mAdapter == null) {
            mAdapter = new MovieAdapter(movieResult, mListener);
            rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovies.setAdapter(mAdapter);
        }
    }
}