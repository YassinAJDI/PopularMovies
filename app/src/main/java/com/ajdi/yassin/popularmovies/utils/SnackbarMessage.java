package com.ajdi.yassin.popularmovies.utils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * A SingleLiveEvent used for Snackbar messages. Like a {@link SingleLiveEvent} but also prevents
 * null messages and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SnackbarMessage extends SingleLiveEvent<Integer> {

    public void observe(LifecycleOwner owner, final SnackbarObserver observer) {
        super.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer t) {
                if (t == null) {
                    return;
                }
                observer.onNewMessage(t);
            }
        });
    }

    public interface SnackbarObserver {
        /**
         * Called when there is a new message to be shown.
         *
         * @param snackbarMessageResourceId The new message, non-null.
         */
        void onNewMessage(@StringRes int snackbarMessageResourceId);
    }
}
