package com.achmad.madeacademy.moviecataloguemvp.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMovieActivity extends AppCompatActivity {

    public ArrayList<Movie> movies;

    public static final String EXTRA_OBJECT = "object_extra";
    public static final String EXTRA_TITLE = "object_title";
    TextView tvTitle, tvRelease, tvOverView, tvCast, tvTitleBar;
    DonutProgress dntProgressDetail;
    ImageView imgPoster, imgBackdrop;
    CircleImageView imgCast;
    Toolbar toolbar;
    AppBarLayout appbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Movie movie;
    String titleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BaseTheme);
        setContentView(R.layout.activity_detail_constraint);
        movie = getIntent().getParcelableExtra(EXTRA_OBJECT);
        titleDetail = getIntent().getStringExtra(EXTRA_TITLE);
        imgBackdrop = findViewById(R.id.image_movie_backdrop);
        tvTitle = findViewById(R.id.tv_title_detail_constraint);
        tvRelease = findViewById(R.id.tv_release_detail_constraint);
        dntProgressDetail = findViewById(R.id.donute_progress_constraint);
        tvOverView = findViewById(R.id.tv_overview_detail_constraint);
        imgPoster = findViewById(R.id.img_poster_constraint);
        imgCast = findViewById(R.id.img_cast_constraint);
        tvCast = findViewById(R.id.tv_cast_constraint);
        toolbar = findViewById(R.id.toolbar_constraint_detail);
        appbar = findViewById(R.id.appbar_constraint_detail);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        tvTitleBar = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }

        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease());
        dntProgressDetail.setProgress(Integer.parseInt(movie.getUser_score()));
        tvOverView.setText(movie.getOverview());
        tvCast.setText(movie.getFeatured_crew());
        Glide.with(this)
                .load(movie.getImg_poster())
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);
        Glide.with(this)
                .load(movie.getImg_featured_crew())
                .into(imgCast);
        Glide.with(this)
                .load(movie.getImg_Backdrop())
                .into(imgBackdrop);
        /* TODO
         *   Make tooltips on button */
    }

    private void handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    tvTitleBar.setText(movie.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }
}
