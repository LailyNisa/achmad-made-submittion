package com.achmad.madeacademy.moviecataloguemvp.ui.discover;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.pref.SettingsActivity;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;
import com.achmad.madeacademy.moviecataloguemvp.utils.CommonUtils;

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
    private DiscoverViewModel movieViewModel;
    private String sortOrder;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        rvMovies = Objects.requireNonNull(getActivity()).findViewById(R.id.rv_movies);
        movieViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortOrder = preferences.getString("reply", "popular_movies");
        reloadData();
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
                        case 422: {
                            CommonUtils.hideLoading();
                            Log.d("Error query", "Error query");
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
        movieViewModel.initPopularMovie();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            movieResult.clear();
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
        movieViewModel.initTopRatedMovie();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            movieResult.clear();
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
        movieViewModel.initDbMovie();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        if (sortOrder.equals("from_db")) {
            inflater.inflate(R.menu.main_menu, menu);
        } else {
            inflater.inflate(R.menu.search_menu, menu);
        }
        androidx.appcompat.widget.SearchView searchView = null;
        MenuItem item = menu.findItem(R.id.action_search);
        if (item != null) {
            searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        }

        if (searchView != null) {
            searchView.setQuery(movieViewModel.getTextMovie().getValue(), true);
            searchView.setQueryHint(getString(R.string.action_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    loadMovieSearch();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Here is where we are going to implement the filter logic
                    if (!newText.isEmpty()) {
                        movieViewModel.setTextMovieSearch(newText);
                        loadMovieSearch();
                    }
                    return true;
                }
            });
            searchView.setOnCloseListener(() -> {
                reloadData();
                return false;
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadMovieSearch() {
        movieViewModel.initMovieSearch();
        movieViewModel.getMovieRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            movieResult.clear();
            try {
                movieResult.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
        });
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }
    private void reloadData() {
        CommonUtils.showLoading(getActivity());
        if (sortOrder.equals("popular_movies")) {
            initPopular();
        } else if (sortOrder.equals("top_rated")) {
            initTopRated();
        } else {
            initDb();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_setting_order) {
            Intent intentSetting = new Intent(this.getActivity(), SettingsActivity.class);
            intentSetting.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentSetting);
        }
        return true;
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
