package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.detail.DetailMovieActivity;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.DiscoverTabLayoutAdapter;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.TvShowAdapter;
import com.google.android.material.tabs.TabLayout;

public class DiscoverActivity extends AppCompatActivity implements MovieAdapter.OnFragmentInteractionListener, TvShowAdapter.OnFragmentInteractionListener {

    DiscoverTabLayoutAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        adapter = new DiscoverTabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new MovieFragment(), getString(R.string.tablayout_movie));
        adapter.addFragment(new TvShowFragment(), getString(R.string.tablayout_tvshow));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Movie Catalogue");
        }
    }

    @Override
    public void onFragmentInteraction(Result movie) {
        movie.setId(movie.getId());
        movie.setTitle(movie.getTitle());
        movie.setReleaseDate(movie.getReleaseDate());
        movie.setVoteAverage(movie.getVoteAverage());
        movie.setPosterPath(movie.getPosterPath());
        movie.setOverview(movie.getOverview());
        movie.setBackdropPath(movie.getBackdropPath());
        Intent moveToDetail = new Intent(this, DetailMovieActivity.class);
        moveToDetail.putExtra(DetailMovieActivity.EXTRA_OBJECT, movie);
        startActivity(moveToDetail);
    }


    @Override
    public void onFragmentInteraction(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShow) {
        tvShow.setName(tvShow.getName());
        tvShow.setFirstAirDate(tvShow.getFirstAirDate());
        tvShow.setVoteAverage(tvShow.getVoteAverage());
        tvShow.setPosterPath(tvShow.getPosterPath());
        tvShow.setOverview(tvShow.getOverview());
        tvShow.setBackdropPath(tvShow.getBackdropPath());
        Intent moveToDetail = new Intent(this, DetailMovieActivity.class);
        moveToDetail.putExtra(DetailMovieActivity.EXTRA_OBJECT_TVSHOW, tvShow);
        startActivity(moveToDetail);
    }
}
