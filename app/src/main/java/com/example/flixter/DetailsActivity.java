package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixter.databinding.ActivityDetailsBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    TextView tvSynopsys;
    RatingBar ratingBar;
    ImageView ivPoster;
    TextView tvVoteCount;
    TextView tvReleaseDate;
    RelativeLayout rvInfo;

    Movie movie;

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tvSynopsys = binding.tvSynopsis;
        ratingBar = binding.rbVoteAverage;
        ivPoster = binding.ivPoster;
        tvVoteCount = binding.tvVoteCount;
        tvReleaseDate = binding.tvReleaseDate;
        rvInfo = binding.rvInfo;

        rvInfo.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_shadow_start_color));
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        tvSynopsys.setText(movie.getOverview());
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(ivPoster);

        ratingBar.setRating(movie.getVoteAverage().floatValue() / 2);
        tvReleaseDate.setText("Release date: " + movie.getReleaseDate());
        tvVoteCount.setText(String.format(" based on %d votes", movie.getVoteCount()));
    }
}