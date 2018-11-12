package com.ajdi.yassin.popularmovies.data.local.model;

import com.ajdi.yassin.popularmovies.data.remote.api.NetworkState;

import androidx.lifecycle.LiveData;

/**
 * @author Yassin Ajdi.
 */
public class RepoMovieDetailsResult {
    public LiveData<Movie> data;
    public LiveData<NetworkState> networkState;

    public RepoMovieDetailsResult(LiveData<Movie> data, LiveData<NetworkState> networkState) {
        this.data = data;
        this.networkState = networkState;
    }
}
