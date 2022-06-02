package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.DetailsActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout rvBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvBack = itemView.findViewById(R.id.rvBack);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            if(getLayoutPosition() % 2 == 0) {
                rvBack.setBackgroundColor(context.getResources().getColor(androidx.cardview.R.color.cardview_shadow_start_color));
            } else {
                rvBack.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Glide.with(context)
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .transform(new RoundedCorners(50))
                        .into(ivPoster);
            } else {
                Glide.with(context)
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .transform(new RoundedCorners(50))
                        .into(ivPoster);
            }

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
