package com.ajdi.yassin.popularmovies.ui.moviedetails.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.data.local.model.Review;
import com.ajdi.yassin.popularmovies.databinding.ItemReviewBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    private ItemReviewBinding binding;

    private Context context;

    public ReviewsViewHolder(@NonNull ItemReviewBinding binding, Context context) {
        super(binding.getRoot());

        this.binding = binding;
        this.context = context;
    }

    public void bindTo(final Review review) {
        binding.textAuthor.setText(review.getAuthor());
        binding.textContent.setText(review.getContent());

        binding.executePendingBindings();
    }

    public static ReviewsViewHolder create(ViewGroup parent) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemReviewBinding binding =
                ItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ReviewsViewHolder(binding, parent.getContext());
    }
}
