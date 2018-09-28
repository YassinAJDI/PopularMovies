package com.ajdi.yassin.popularmoviespart1.data;

/**
 * @author Yassin Ajdi.
 */
public class NetworkState {

    static NetworkState LOADED = new NetworkState(Status.SUCCESS, null);
    static NetworkState LOADING = new NetworkState(Status.RUNNING, null);

    private NetworkState(Status status, String msg) {
    }

    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }
}

enum Status {
    RUNNING,
    SUCCESS,
    FAILED
}
