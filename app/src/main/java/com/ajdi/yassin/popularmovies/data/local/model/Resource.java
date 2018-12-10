package com.ajdi.yassin.popularmovies.data.local.model;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic class that contains data and status about loading this data.
 * <p>
 * You can read more about it in the [Architecture Guide]
 * (https://developer.android.com/jetpack/docs/guide#addendum).
 */
public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * Creates [Resource] object with `SUCCESS` status and [data].
     */
    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    /**
     * Creates [Resource] object with `ERROR` status and [message].
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    /**
     * Creates [Resource] object with `LOADING` status to notify
     * the UI to show loading.
     */
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource<?> resource = (Resource<?>) o;
        return status == resource.status &&
                Objects.equals(data, resource.data) &&
                Objects.equals(message, resource.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(status, data, message);
    }

    /**
     * Status of a resource that is provided to the UI.
     * <p>
     * These are usually created by the Repository classes where they return
     * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
     */
    public enum Status {
        SUCCESS, ERROR, LOADING
    }
}
