package com.ajdi.yassin.popularmoviespart1.ui.movieslist;

import android.os.Bundle;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.data.model.Movie;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;
import com.ajdi.yassin.popularmoviespart1.utils.GlideRequests;

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
        return ViewModelProviders.of(this).get(MoviesViewModel.class);
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = findViewById(R.id.rv_movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        GlideRequests glideRequests = GlideApp.with(this);
        final MoviesAdapter moviesAdapter = new MoviesAdapter(glideRequests);
        recyclerView.setAdapter(moviesAdapter);

        // observe paged list
        viewModel.getPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                moviesAdapter.submitList(movies);
            }
        });
    }
}
