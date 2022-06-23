package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.ActorAdapter;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.databinding.ActivityDetailsBinding;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.models.Actor;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "Details activity";
    Movie movie;
    ActivityDetailsBinding binding;


    List<Actor> actorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.rvInfo.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_shadow_start_color));
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        binding.tvSynopsis.setText(movie.getOverview());
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(binding.ivPoster);

        binding.rbVoteAverage.setRating(movie.getVoteAverage().floatValue() / 2);
        binding.tvReleaseDate.setText("Release date: " + movie.getReleaseDate());
        binding.tvVoteCount.setText(String.format(" based on %d votes", movie.getVoteCount()));
        binding.tvGenres.setText(movie.getGenres());

        binding.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrailerActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                startActivity(intent);
            }
        });
        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActorAdapter actorAdapter = new ActorAdapter(this, actorList);
            binding.rvActors.setAdapter(actorAdapter);
            binding.rvActors.setLayoutManager(new LinearLayoutManager(this));
            loadActors(actorAdapter);
        }

    }

    private void loadActors(ActorAdapter adapter) {
        AsyncHttpClient client = new AsyncHttpClient();
        String targetUrl = String.format("https://api.themoviedb.org/3/movie/%d/credits?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", movie.getId());
        client.get(targetUrl, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON response) {
                        Log.d(TAG, "on success");
                        JSONObject object = response.jsonObject;
                        try {
                            JSONArray results = object.getJSONArray("cast");
                            Log.i(TAG, "results: " + results.toString());
                            actorList.addAll(Actor.fromJsonArray(results));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        Log.d(TAG, errorResponse);
                    }
                }
        );
    }
}