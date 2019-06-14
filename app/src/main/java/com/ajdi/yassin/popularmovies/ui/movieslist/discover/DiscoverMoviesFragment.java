package com.ajdi.yassin.popularmovies.ui.movieslist.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.local.model.Movie;
import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesActivity;
import com.ajdi.yassin.popularmovies.ui.movieslist.MoviesFilterType;
import com.ajdi.yassin.popularmovies.utils.Injection;
import com.ajdi.yassin.popularmovies.utils.ItemOffsetDecoration;
import com.ajdi.yassin.popularmovies.utils.UiUtils;
import com.ajdi.yassin.popularmovies.utils.ViewModelFactory;

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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_discover_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = obtainViewModel(getActivity());
        setupListAdapter();

        // Observe current toolbar title
        viewModel.getCurrentTitle().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer title) {
                ((MoviesActivity) getActivity()).getSupportActionBar().setTitle(title);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        UiUtils.tintMenuIcon(getActivity(), menu.findItem(R.id.action_sort_by), R.color.md_white_1000);

        if (viewModel.getCurrentSorting() == MoviesFilterType.POPULAR) {
            menu.findItem(R.id.action_popular_movies).setChecked(true);
        } else if (viewModel.getCurrentSorting() == MoviesFilterType.TOP_RATED) {
            menu.findItem(R.id.action_top_rated).setChecked(true);
        } else {
            menu.findItem(R.id.action_now_playing).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.menu_sort_group) {
            viewModel.setSortMoviesBy(item.getItemId());
            item.setChecked(true);
        }

        return super.onOptionsItemSelected(item);
    }

    public static DiscoverMoviesViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(DiscoverMoviesViewModel.class);
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_movie_list);
        final DiscoverMoviesAdapter discoverMoviesAdapter =
                new DiscoverMoviesAdapter(viewModel);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

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
        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {
                discoverMoviesAdapter.setNetworkState(resource);
            }
        });
    }
}
