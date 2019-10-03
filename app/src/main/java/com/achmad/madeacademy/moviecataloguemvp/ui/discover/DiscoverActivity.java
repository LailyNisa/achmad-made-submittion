package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

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

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.Movie;
import com.achmad.madeacademy.moviecataloguemvp.ui.detail.DetailMovieActivity;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.DiscoverTabLayoutAdapter;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.ListDiscoverAdapter;
import com.google.android.material.tabs.TabLayout;

public class DiscoverActivity extends AppCompatActivity implements ListDiscoverAdapter.OnFragmentInteractionListener {

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
            getSupportActionBar().setTitle("Popular Movies");
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
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Movie movie) {
        Movie movieObject = new Movie();
        movieObject.setTitle(movie.getTitle());
        movieObject.setRelease(movie.getRelease());
        movieObject.setUser_score(movie.getUser_score());
        movieObject.setImg_poster(movie.getImg_poster());
        movieObject.setOverview(movie.getOverview());
        movieObject.setImg_featured_crew(movie.getImg_featured_crew());
        movieObject.setFeatured_crew(movie.getFeatured_crew());
        movieObject.setImg_Backdrop(movie.getImg_Backdrop());
        Intent moveToDetail = new Intent(this, DetailMovieActivity.class);
        moveToDetail.putExtra(DetailMovieActivity.EXTRA_OBJECT, movieObject);
        startActivity(moveToDetail);
    }
}
