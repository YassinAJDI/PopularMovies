package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.view.ViewGroup;

import com.ajdi.yassin.popularmoviespart1.data.model.Trailer;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi.
 */
public class TrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Trailer> trailers;

    public TrailersAdapter(List<Trailer> trailers, GlideRequests glide) {
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TrailerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        ((TrailerViewHolder) holder).bindTo(trailer);
    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }
}
