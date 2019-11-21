package com.achmad.madeacademy.moviecataloguemvp.ui.discover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.detail.DetailMovieActivity;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.DiscoverTabLayoutAdapter;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.MovieAdapter;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.adapter.TvShowAdapter;
import com.achmad.madeacademy.moviecataloguemvp.utils.AlarmReceiver;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DiscoverActivity extends AppCompatActivity implements MovieAdapter.OnFragmentInteractionListener, TvShowAdapter.OnFragmentInteractionListener {

    DiscoverTabLayoutAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    boolean dailyReminder;
    boolean releaseReminder;
    boolean isReminderSet;
    boolean isReminderReleaseSet;
    private AlarmReceiver alarmReceiver;
    private DiscoverViewModel discoverViewModel;
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
        alarmReceiver = new AlarmReceiver();
        discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        dailyReminder = preferences.getBoolean("daily_notification", false);
        releaseReminder = preferences.getBoolean("release_notification", false);
        isReminderSet = alarmReceiver.isAlarmSet(this, AlarmReceiver.TYPE_REPEATING);
        isReminderReleaseSet = alarmReceiver.isAlarmSet(this, AlarmReceiver.TYPE_ONE_TIME);
        loadReleaseReminder();
        loadDailyReminder();

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

    private void loadDailyReminder() {
        if (dailyReminder) {
            String repeatTime = "15:31";
            String repeatMessage = "Hy I Miss you i'm daily reminder";
            if (isReminderSet) {
                Log.d("ReminderAlarm", "Already set");
            } else {
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage);
            }
        } else {
            if (isReminderSet) {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
            } else {
                Log.d("ReminderAlarm", "No Alarm Set");
            }
        }
    }

    private void loadReleaseReminder() {
        if (releaseReminder) {
            String repeatTime = "15:32";
            String repeatMessage = "Hay release cooy";
            ArrayList<Result> movieResult = new ArrayList<>();
            discoverViewModel.initMovieReleaseToday("2019-01-31");
            discoverViewModel.getMovieRelease().observe(this, movie -> {
                movieResult.addAll(movie.getResults());
            });
            if (isReminderReleaseSet) {
                Log.d("ReminderAlarm", "Already set");
            } else {
                alarmReceiver.setRepeatingAlarmRelease(this, AlarmReceiver.TYPE_ONE_TIME,
                        repeatTime, repeatMessage);
            }

        } else {
            if (isReminderReleaseSet) {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_ONE_TIME);
            } else {
                Log.d("ReminderAlarm", "No Alarm Set");
                Toast.makeText(this, "Gak ada release", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
