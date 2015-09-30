package common.android.utils.network;

import common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by conrad on 2015/03/09.
 */
public abstract class Client<T> implements ILogTag {

    @Nullable
    protected ClientListener<T> mListener;
    protected RequestExecutor mRequestExecutor;
    @Nullable
    protected Request<T> mCurrentRequest;

    public Client(final RequestExecutor requestExecutor) {
        mRequestExecutor = requestExecutor;
    }

    @NotNull
    final public String tag() {
        return getClass().getSimpleName();
    }

    public void cancelRequest() {
        mListener = null;
    }

    protected void setListener(final ClientListener<T> listener) {
        mListener = listener;
    }

    @org.jetbrains.annotations.Nullable
    protected abstract Request<T> buildRequest();

    // Methods shall be called on the main thread
    public interface ClientListener<E> {
        void onSuccess(Client<E> client, E result);

        void onError(Client<E> client, Exception error);
    }


}
