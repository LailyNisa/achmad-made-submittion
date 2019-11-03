package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    public void onFragmentInteraction(Result movie) {
        movie.setTitle(movie.getTitle());
        movie.setReleaseDate(movie.getReleaseDate());
        movie.setVoteAverage(movie.getVoteAverage());
        movie.setPosterPath(movie.getPosterPath());
        movie.setOverview(movie.getOverview());
//        movie.setImg_featured_crew(movie.getImg_featured_crew());
//        movie.setFeatured_crew(movie.getFeatured_crew());
        movie.setBackdropPath(movie.getBackdropPath());
        Intent moveToDetail = new Intent(this, DetailMovieActivity.class);
        moveToDetail.putExtra(DetailMovieActivity.EXTRA_OBJECT, movie);
        Toast.makeText(this, movie.getTitle(), Toast.LENGTH_SHORT).show();
        startActivity(moveToDetail);
    }


    @Override
    public void onFragmentInteraction(com.achmad.madeacademy.moviecataloguemvp.data.remote.model.tvshow.Result tvShow) {
        tvShow.setName(tvShow.getName());
        tvShow.setFirstAirDate(tvShow.getFirstAirDate());
        tvShow.setVoteAverage(tvShow.getVoteAverage());
        tvShow.setPosterPath(tvShow.getPosterPath());
        tvShow.setOverview(tvShow.getOverview());
//        movie.setImg_featured_crew(movie.getImg_featured_crew());
//        movie.setFeatured_crew(movie.getFeatured_crew());
        tvShow.setBackdropPath(tvShow.getBackdropPath());
        Intent moveToDetail = new Intent(this, DetailMovieActivity.class);
        moveToDetail.putExtra(DetailMovieActivity.EXTRA_OBJECT_TVSHOW, tvShow);
        Toast.makeText(this, tvShow.getName(), Toast.LENGTH_SHORT).show();
        startActivity(moveToDetail);
    }
}
