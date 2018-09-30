package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import android.view.View;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi.
 */
public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder> {

    public MoviesAdapter(GlideRequests glide) {
        super(new DiffUtil.ItemCallback<Movie>() {
            @Override
            public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // Callback
//    public static DiffCallback<Movie> DIFF_CALLBACK = new DiffCallback<Movie>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
//            return oldItem.id == newItem.id;
//        }
//        @Override
//        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
//            return oldItem.equals(newItem);
//        }
//    };
}
