package com.ajdi.yassin.popularmovies.data;

import com.ajdi.yassin.popularmovies.data.local.model.Resource;
import com.ajdi.yassin.popularmovies.data.remote.api.ApiResponse;
import com.ajdi.yassin.popularmovies.utils.AppExecutors;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * <p>
 * You can read more about it in the [Architecture Guide]
 * (https://developer.android.com/jetpack/docs/guide#addendum).
 *
 * @param <ResultType>
 * @param <RequestType> </RequestType></ResultType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    /**
     * The final result LiveData
     */
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    private AppExecutors mExecutors;

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        mExecutors = appExecutors;
        // Send loading state to UI
        result.setValue(Resource.<ResultType>loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    /**
     * Fetch the data from network and persist into DB and then
     * send it back to UI.
     */
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                setValue(Resource.loading(newData));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(final ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                if (response.isSuccessful()) {
                    mExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult(response.body);
                            mExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    // we specially request a new live data,
                                    // otherwise we will get immediately last cached value,
                                    // which may not be updated with latest results received from network.
                                    result.addSource(loadFromDb(), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(ResultType newData) {
                                            setValue(Resource.success(newData));
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            setValue(Resource.error(response.getError().getMessage(), newData));
                        }
                    });
                }
            }
        });
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @NonNull
    @MainThread
    protected abstract void onFetchFailed();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
