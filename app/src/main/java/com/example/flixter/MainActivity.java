package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=612bbb4ea684396b069174e4e0674e76";
    public static final String TAG = "Main activity";
    List<Movie> movies = new ArrayList<>();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        RecyclerView rvMovies = binding.rvMovies;

        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON response) {
                        Log.d(TAG, "on success");
                        JSONObject object = response.jsonObject;
                        try {
                            JSONArray results = object.getJSONArray("results");
                            Log.i(TAG, "results: " + results.toString());
                            movies.addAll(Movie.fromJsonArray(results));
                            movieAdapter.notifyDataSetChanged();
                            Log.i(TAG, "size" + movies.size());
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