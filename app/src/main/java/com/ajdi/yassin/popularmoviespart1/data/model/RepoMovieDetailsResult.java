package com.ajdi.yassin.popularmoviespart1.data.model;

import com.ajdi.yassin.popularmoviespart1.data.api.NetworkState;

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
