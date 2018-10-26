package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.model.Trailer;
import com.ajdi.yassin.popularmoviespart1.databinding.ItemTrailerBinding;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder {

    ItemTrailerBinding binding;

    GlideRequests glide;

    public TrailerViewHolder(@NonNull ItemTrailerBinding binding, GlideRequests glide) {
        super(binding.getRoot());

        this.binding = binding;
        this.glide = glide;
    }

    public void bindTo(Trailer trailer) {
        String thumbnail =
                "https://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg";

        glide
                .load(thumbnail)
                .placeholder(R.color.md_grey_200)
                .into(binding.imageTrailer);

        binding.executePendingBindings();
    }

    static TrailerViewHolder create(ViewGroup parent, GlideRequests glide) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemTrailerBinding binding =
                ItemTrailerBinding.inflate(layoutInflater, parent, false);
        return new TrailerViewHolder(binding, glide);
    }
}
