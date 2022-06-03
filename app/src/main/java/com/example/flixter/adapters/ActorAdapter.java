package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.example.flixter.models.Actor;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {

    List<Actor> actors;
    Context context;

    public ActorAdapter(Context context, List<Actor> actors) {
        this.context = context;
        this.actors = actors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View actorView = LayoutInflater.from(context).inflate(R.layout.item_actor, parent, false);
        return new ActorAdapter.ViewHolder(actorView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Actor actor = actors.get(position);
        holder.bind(actor);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDepartment;
        TextView tvCharacter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvCharacter = itemView.findViewById(R.id.tvCharacter);
        }

        public void bind(Actor actor) {
            tvName.setText(actor.getName());
            tvDepartment.setText(actor.getDepartment());
            tvCharacter.setText(actor.getCharacter());
        }
    }
}
