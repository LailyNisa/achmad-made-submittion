package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.local.DiscoverContract;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.NetworkRepository;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;
import com.achmad.madeacademy.moviecataloguemvp.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements LoadMovieCallBack {
    private RecyclerView rvMovies;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter.OnFragmentInteractionListener mListener;
    private MovieAdapter mAdapter;
    private MovieViewModel movieViewModel;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    public MovieFragment() {
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, mAdapter.getListMovies());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        CommonUtils.showLoading(getActivity());
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString("reply","popular_movies");
        if (sortOrder.equals("popular_movies")){
            initPopular();
        } else if (sortOrder.equals("top_rated")) {
            initTopRated();
        }else {
            HandlerThread handlerThread = new HandlerThread("DataObserver");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            DataObserver myObserver = new DataObserver(handler, getActivity());
            getActivity().getContentResolver().registerContentObserver(DiscoverContract.MOVIE_URI, true, myObserver);
            if (savedInstanceState == null) {
                initDb();
                Log.d("savedInstance", "Ada");
            } else {
                Log.d("savedInstance", "Tidak Ada");
                ArrayList<Result> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
                if (list != null) {
                    mAdapter = new MovieAdapter(list, mListener);
                    rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvMovies.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
//            initDb();
        }
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


    private void initPopular() {
        movieViewModel.initPopular();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                movieResult.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }

            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initTopRated() {
        movieViewModel.initTopRated();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                movieResult.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }

            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initDb() {
//        movieViewModel.initDb();
//        movieViewModel.getMovieDb().observe(getViewLifecycleOwner(), movieResponse -> {
//            CommonUtils.hideLoading();
//            try {
//                movieResult.addAll(movieResponse);
//            } catch (Exception e) {
//                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
//            }
//            setRvMovies();
//            mAdapter.notifyDataSetChanged();
//        });
        new NetworkRepository.LoadMovieAsyncTask(getActivity(), this);
    }


    public static MovieFragment newInstance() {
        return new MovieFragment();
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

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> CommonUtils.showLoading(getActivity()));
    }

    @Override
    public void postExecute(ArrayList<Result> movie) {
        CommonUtils.hideLoading();
        movieResult.addAll(movie);
        mAdapter = new MovieAdapter(movieResult, mListener);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String sortOrder = preferences.getString("reply", "from_db");
            if (sortOrder.equals("from_db")) {
                new NetworkRepository.LoadMovieAsyncTask(context, (LoadMovieCallBack) context).execute();
            }
        }
    }
}
