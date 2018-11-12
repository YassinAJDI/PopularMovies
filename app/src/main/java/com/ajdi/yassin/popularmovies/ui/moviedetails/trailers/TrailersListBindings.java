package com.ajdi.yassin.popularmovies.ui.moviedetails.trailers;

import com.ajdi.yassin.popularmovies.data.local.model.Trailer;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi
 * @since 11/11/2018.
 */
public class TrailersListBindings {

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<Trailer> items) {
        TrailersAdapter adapter = (TrailersAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items);
        }
    }
}
