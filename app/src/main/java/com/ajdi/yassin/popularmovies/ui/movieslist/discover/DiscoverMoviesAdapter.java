package com.ajdi.yassin.popularmovies.ui.movieslist.discover;

import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.ui.movieslist.MovieViewHolder;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter implementation that shows a list of Movie posters
 *
 * @author Yassin Ajdi.
 */
public class DiscoverMoviesAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private DiscoverMoviesViewModel mViewModel;

    private Resource resource = null;

    DiscoverMoviesAdapter(DiscoverMoviesViewModel viewModel) {
        super(MOVIE_COMPARATOR);

        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_movie:
                return MovieViewHolder.create(parent);
            case R.layout.item_network_state:
                return NetworkStateViewHolder.create(parent, mViewModel);
            default:
                throw new IllegalArgumentException("unknown view type " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_movie:
                ((MovieViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.item_network_state:
                ((NetworkStateViewHolder) holder).bindTo(resource);
                break;
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_movie;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    private boolean hasExtraRow() {
        return resource != null && resource.status != Resource.Status.SUCCESS;
    }

    public void setNetworkState(Resource resource) {
        Resource previousState = this.resource;
        boolean hadExtraRow = hasExtraRow();
        this.resource = resource;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && !previousState.equals(resource)) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private static DiffUtil.ItemCallback<Movie> MOVIE_COMPARATOR = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };
}
