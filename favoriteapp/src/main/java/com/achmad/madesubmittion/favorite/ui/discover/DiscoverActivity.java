package com.achmad.madesubmittion.favorite.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.achmad.madesubmittion.favorite.R;
import com.achmad.madesubmittion.favorite.data.remote.model.movie.Result;
import com.achmad.madesubmittion.favorite.ui.detail.DetailMovieActivity;
import com.achmad.madesubmittion.favorite.ui.discover.adapter.DiscoverTabLayoutAdapter;
import com.achmad.madesubmittion.favorite.ui.discover.adapter.MovieAdapter;
import com.achmad.madesubmittion.favorite.ui.discover.adapter.TvShowAdapter;
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
            getSupportActionBar().setTitle("Favorite Catalogue");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Intent i = new Intent(this, DiscoverActivity.class);
//        finish();
//        startActivity(i);
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
    public void onFragmentInteraction(com.achmad.madesubmittion.favorite.data.remote.model.tvshow.Result tvShow) {
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
