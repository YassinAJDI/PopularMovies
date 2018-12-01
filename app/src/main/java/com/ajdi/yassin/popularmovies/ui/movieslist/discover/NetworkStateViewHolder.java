package com.ajdi.yassin.popularmovies.ui.movieslist.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.databinding.ItemNetworkStateBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 *
 * @author Yassin Ajdi.
 */
public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private ItemNetworkStateBinding binding;

    public NetworkStateViewHolder(@NonNull ItemNetworkStateBinding binding,
                                  final DiscoverMoviesViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;

        // Trigger retry event on click
        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.retry();
            }
        });
    }

    public static NetworkStateViewHolder create(ViewGroup parent, DiscoverMoviesViewModel viewModel) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemNetworkStateBinding binding =
                ItemNetworkStateBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, viewModel);
    }

    public void bindTo(Resource resource) {
        binding.setResource(resource);
        binding.executePendingBindings();
    }
}
