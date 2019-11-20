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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result;
import com.achmad.madeacademy.moviecataloguemvp.pref.SettingsActivity;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.TvShowAdapter;
import com.achmad.madeacademy.moviecataloguemvp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView rvTvShow;
    private ArrayList<Result> tvShowList = new ArrayList<>();
    private TvShowAdapter.OnFragmentInteractionListener mListener;
    private TvShowAdapter mAdapter;
    private DiscoverViewModel tvShowViewModel;
    private String sortOrder;
    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rvTvShow = (RecyclerView) view;
            rvTvShow.setHasFixedSize(true);
            rvTvShow.setLayoutManager(new LinearLayoutManager(context));
            rvTvShow.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvShowViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
        sortOrder = preferences.getString("reply", "popular_movies");
        reloadData();
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }

    private void initPopular() {
        tvShowViewModel.initPopularTvShow();
        tvShowViewModel.getTvShowRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            tvShowList.clear();
            try {
                tvShowList.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initTopRated() {
        tvShowViewModel.initTopRatedTvShow();
        tvShowViewModel.getTvShowRepository().observe(getViewLifecycleOwner(), movieResponse -> {
            tvShowList.clear();
            try {
                tvShowList.addAll(movieResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
            setRvMovies();
            mAdapter.notifyDataSetChanged();
        });
    }
    private void initDb() {
        tvShowViewModel.initDbTvShow();
        tvShowViewModel.getTvShowDb().observe(getViewLifecycleOwner(), movieResponse -> {
            CommonUtils.hideLoading();
            try {
                tvShowList.addAll(movieResponse);
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
            inflater.inflate(R.menu.search_menu_tvfragment, menu);
        }
        SearchView searchView = null;
        MenuItem item = menu.findItem(R.id.action_search_tvfragment);

        searchView = (SearchView) item.getActionView();

        if (searchView != null) {
            searchView.setQuery(tvShowViewModel.getTextTvSearch().getValue(), true);
            searchView.setQueryHint(getString(R.string.action_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    loadTvSearch();
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        tvShowViewModel.setTextTvSearch(newText);
                        loadTvSearch();
                    }
                    // Here is where we are going to implement the filter logic
                    return true;
                }
            });
            searchView.setOnCloseListener(() -> {
                tvShowViewModel.setTextTvSearch("");
                reloadData();
                return false;
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadTvSearch() {
        tvShowViewModel.initTvSearch();
        tvShowViewModel.getTvShowRepository().observe(getViewLifecycleOwner(), tvShowResponse -> {
            tvShowList.clear();
            try {
                tvShowList.addAll(tvShowResponse.getResults());
            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getMessage()));
            }
        });
        setRvMovies();
        mAdapter.notifyDataSetChanged();
    }

    private void reloadData() {
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TvShowAdapter.OnFragmentInteractionListener) {
            mListener = (TvShowAdapter.OnFragmentInteractionListener) context;
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
            mAdapter = new TvShowAdapter(tvShowList, mListener);
            rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvTvShow.setAdapter(mAdapter);
        }
    }
}
