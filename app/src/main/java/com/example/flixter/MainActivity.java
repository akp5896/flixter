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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static String NOW_PLAYING_URL;
    public static String GENRES_URL;
    public static final String TAG = "Main activity";
    List<Movie> movies = new ArrayList<>();
    public static HashMap<Integer, String> genres = new HashMap<>();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NOW_PLAYING_URL = String.format("https://api.themoviedb.org/3/movie/now_playing?api_key=%s", getString(R.string.movie_db_api_key));
        GENRES_URL = String.format("https://api.themoviedb.org/3/genre/movie/list?api_key=%s", getString(R.string.movie_db_api_key));

        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        binding.rvMovies.setAdapter(movieAdapter);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        loadGenres(client);
        loadMoviesList(movieAdapter, client);
    }

    private void loadGenres(AsyncHttpClient client) {
        client.get(GENRES_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject object = json.jsonObject;
                try {
                    JSONArray results = object.getJSONArray("genres");
                    for(int i = 0; i < results.length(); i++) {
                        JSONObject genre = results.getJSONObject(i);
                        genres.put(genre.getInt("id"), genre.getString("name"));
                    }
                    for(Map.Entry<Integer, String> entry : genres.entrySet()) {
                        Log.d(TAG, entry.getKey() + ": " + entry.getValue());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Unable to load genres" + response);
            }
        });
    }

    private void loadMoviesList(MovieAdapter movieAdapter, AsyncHttpClient client) {
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