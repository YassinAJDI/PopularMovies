package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;
import com.ajdi.yassin.popularmoviespart1.utils.Injection;
import com.ajdi.yassin.popularmoviespart1.utils.ItemOffsetDecoration;
import com.ajdi.yassin.popularmoviespart1.utils.UiUtils;
import com.ajdi.yassin.popularmoviespart1.utils.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity {

    MoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = obtainViewModel();
        setupToolbar();
        setupListAdapter();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel.getCurrentTitle().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setTitle(integer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        UiUtils.tintMenuIcon(this, menu.findItem(R.id.action_sort_by), R.color.md_white_1000);

        if (viewModel.getCurrentSorting() == MoviesFilterType.POPULAR) {
            menu.findItem(R.id.action_popular_movies).setChecked(true);
        } else {
            menu.findItem(R.id.action_top_rated).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.menu_sort_group) {
            viewModel.setSortMoviesBy(item.getItemId());
            item.setChecked(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private MoviesViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(Injection.provideMovieRepository());
        return ViewModelProviders.of(this, factory).get(MoviesViewModel.class);
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = findViewById(R.id.rv_movie_list);
        GlideRequests glideRequests = GlideApp.with(this);
        final MoviesAdapter moviesAdapter = new MoviesAdapter(glideRequests, viewModel);
        recyclerView.setAdapter(moviesAdapter);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        // draw network status and errors to fit the whole row(3 spans)
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (moviesAdapter.getItemViewType(position)) {
                    case R.layout.item_network_state:
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration =
                new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        // observe paged list
        viewModel.getPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                moviesAdapter.submitList(movies);
            }
        });

        // observe network state
        viewModel.getNetWorkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                moviesAdapter.setNetworkState(networkState);
            }
        });
    }
}
