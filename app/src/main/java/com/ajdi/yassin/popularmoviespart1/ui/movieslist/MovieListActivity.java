package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import android.os.Bundle;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;
import com.ajdi.yassin.popularmoviespart1.utils.Injection;
import com.ajdi.yassin.popularmoviespart1.utils.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListActivity extends AppCompatActivity {

    MoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = obtainViewModel();
        setupListAdapter();
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

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        // span network status and errors to fit the whole row(3 spans)
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (moviesAdapter.getItemViewType(position)) {
                    case R.layout.item_network_state:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);

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
