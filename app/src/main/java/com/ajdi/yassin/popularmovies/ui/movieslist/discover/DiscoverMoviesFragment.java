package com.ajdi.yassin.popularmovies.ui.movieslist.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.remote.api.NetworkState;
import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesActivity;
import com.ajdi.yassin.popularmovies.utils.GlideApp;
import com.ajdi.yassin.popularmovies.utils.GlideRequests;
import com.ajdi.yassin.popularmovies.utils.ItemOffsetDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi.
 */
public class DiscoverMoviesFragment extends Fragment {

    private DiscoverMoviesViewModel viewModel;

    public static DiscoverMoviesFragment newInstance() {
        return new DiscoverMoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = MoviesActivity.obtainViewModel(getActivity());
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_movie_list);
        GlideRequests glideRequests = GlideApp.with(this);
        final DiscoverMoviesAdapter discoverMoviesAdapter = new DiscoverMoviesAdapter(glideRequests, viewModel);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.span_count));

        // draw network status and errors messages to fit the whole row(3 spans)
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (discoverMoviesAdapter.getItemViewType(position)) {
                    case R.layout.item_network_state:
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });

        // setup recyclerView
        recyclerView.setAdapter(discoverMoviesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        // observe paged list
        viewModel.getPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                discoverMoviesAdapter.submitList(movies);
            }
        });

        // observe network state
        viewModel.getNetWorkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                discoverMoviesAdapter.setNetworkState(networkState);
            }
        });
    }
}
