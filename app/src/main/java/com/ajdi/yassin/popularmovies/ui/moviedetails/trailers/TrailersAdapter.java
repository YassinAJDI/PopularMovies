package com.ajdi.yassin.popularmovies.ui.moviedetails.trailers;

import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.data.local.model.Trailer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi.
 */
public class TrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Trailer> trailerList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TrailerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        ((TrailerViewHolder) holder).bindTo(trailer);
    }

    @Override
    public int getItemCount() {
        return trailerList != null ? trailerList.size() : 0;
    }

    public void submitList(List<Trailer> trailers) {
        trailerList = trailers;
        notifyDataSetChanged();
    }
}
