package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.databinding.ItemMovieBinding;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.ajdi.yassin.popularmoviespart1.ui.movieslist.MoviesAdapter.IMAGE_URL;

/**
 * @author Yassin Ajdi.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final ItemMovieBinding binding;
    private GlideRequests glide;

    public MovieViewHolder(@NonNull ItemMovieBinding binding, GlideRequests glide) {
        super(binding.getRoot());

        this.binding = binding;
        this.glide = glide;
    }

    void bindTo(Movie movie) {

        // movie poster
        glide
                .load(IMAGE_URL + movie.getImageUrl())
                .placeholder(android.R.color.holo_red_dark)
                .into(binding.imageMoviePoster);

        binding.executePendingBindings();
    }

    static MovieViewHolder create(ViewGroup parent, GlideRequests glide) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemMovieBinding binding =
                ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding, glide);
    }
}
