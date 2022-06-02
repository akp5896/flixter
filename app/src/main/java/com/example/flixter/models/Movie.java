package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String title;
    String posterPath;
    String overview;
    String backdropPath;
    Double voteAverage;
    Integer voteCount;
    String releaseDate;

    public Movie() {

    }

    public Movie(JSONObject jsonObject) {
        try {
            title = jsonObject.getString("title");
            posterPath = jsonObject.getString("poster_path");
            overview = jsonObject.getString("overview");
            backdropPath = jsonObject.getString("backdrop_path");
            voteAverage = jsonObject.getDouble("vote_average");
            voteCount = jsonObject.getInt("vote_count");
            releaseDate = jsonObject.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Movie> fromJsonArray(JSONArray array) {
        List<Movie> movies = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {

                    movies.add(new Movie(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }

    public String getOverview() {
        return overview;
    }
}
