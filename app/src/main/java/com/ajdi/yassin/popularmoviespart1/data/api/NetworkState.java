package com.ajdi.yassin.popularmoviespart1.data.api;

/**
 * @author Yassin Ajdi.
 */
public class NetworkState {

    private Status status;
    private String msg;

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, null);
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, null);

    private NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static NetworkState error(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

