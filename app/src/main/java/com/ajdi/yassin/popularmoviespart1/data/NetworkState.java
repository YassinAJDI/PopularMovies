package com.ajdi.yassin.popularmoviespart1.data;

/**
 * @author Yassin Ajdi.
 */
public class NetworkState {

    private Status status;
    private String msg;

    private NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static NetworkState LOADED = new NetworkState(Status.SUCCESS, null);
    static NetworkState LOADING = new NetworkState(Status.RUNNING, null);
    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }
}

enum Status {
    RUNNING,
    SUCCESS,
    FAILED
}
